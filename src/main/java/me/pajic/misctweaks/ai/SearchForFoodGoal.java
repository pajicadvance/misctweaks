package me.pajic.misctweaks.ai;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

//~ if <26.1 'equine.AbstractHorse' -> 'horse.AbstractHorse'
import net.minecraft.world.entity.animal.equine.AbstractHorse;

public class SearchForFoodGoal<T extends Animal> extends Goal {

    private static final Map<ItemEntity, Set<Animal>> CLAIMS = new WeakHashMap<>();
    private static final int MAX_SEARCH_TICKS = 400;

    private final T animal;
    private final double speedModifier;
    private final float within;
    private final Map<ItemEntity, Long> unreachable = new WeakHashMap<>();
    @Nullable private ItemEntity target;
    @Nullable private Path path;
    private int cooldown;
    private int ticksRunning;
    private int timeToRecalcPath;
    private boolean gaveUp;

    public SearchForFoodGoal(T animal, double speedModifier, float within) {
        this.animal = animal;
        this.speedModifier = speedModifier;
        this.within = within;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @SuppressWarnings("resource")
    @Override
    public boolean canUse() {
        if (cooldown > 0) {
            cooldown--;
            return false;
        }
        if (!canEat() || animal.getRandom().nextInt(reducedTickDelay(20)) != 0) return false;
        long time = animal.level().getGameTime();
        unreachable.values().removeIf(until -> until <= time);
        List<ItemEntity> nearbyFoods = animal.level().getEntitiesOfClass(
                ItemEntity.class,
                animal.getBoundingBox().inflate(within, 4, within),
                itemEntity -> animal.isFood(itemEntity.getItem()) && !unreachable.containsKey(itemEntity) && hasUnclaimedFood(itemEntity)
        );
        nearbyFoods.sort(Comparator.comparingDouble(animal::distanceToSqr));
        for (int i = 0; i < Math.min(nearbyFoods.size(), 3); i++) {
            ItemEntity food = nearbyFoods.get(i);
            Path foodPath = animal.getNavigation().createPath(food, 1);
            if (foodPath != null && foodPath.canReach()) {
                target = food;
                path = foodPath;
                return true;
            }
            markUnreachable(food);
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return target != null &&
                !gaveUp &&
                target.isAlive() &&
                ticksRunning < MAX_SEARCH_TICKS &&
                target.distanceToSqr(animal) < within * within &&
                canEat();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        ticksRunning = 0;
        gaveUp = false;
        timeToRecalcPath = adjustedTickDelay(10);
        CLAIMS.computeIfAbsent(target, i -> new HashSet<>()).add(animal);
        animal.getNavigation().moveTo(path, speedModifier);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void tick() {
        ticksRunning++;
        if (animal.getBoundingBox().inflate(1, 1, 1).intersects(target.getBoundingBox()) && !target.hasPickUpDelay()) {
            eat(target);
            return;
        }
        if (--timeToRecalcPath <= 0) {
            timeToRecalcPath = adjustedTickDelay(10);
            if (!animal.getNavigation().moveTo(target, speedModifier)) {
                markUnreachable(target);
                gaveUp = true;
            }
        }
    }

    @Override
    public void stop() {
        if (target != null) {
            if (ticksRunning >= MAX_SEARCH_TICKS && target.isAlive()) markUnreachable(target);
            Set<Animal> claimants = CLAIMS.get(target);
            if (claimants != null) {
                claimants.remove(animal);
                if (claimants.isEmpty()) CLAIMS.remove(target);
            }
        }
        target = null;
        path = null;
        cooldown = reducedTickDelay(40);
        animal.getNavigation().stop();
    }

    private boolean canEat() {
        if (animal.isBaby() || animal.getAge() != 0 || !animal.canFallInLove()) return false;
        if (animal instanceof AbstractHorse horse && (!horse.isTamed() || horse.getHealth() < horse.getMaxHealth())) return false;
        if (animal instanceof TamableAnimal tamableAnimal && (!tamableAnimal.isTame() || tamableAnimal.isOrderedToSit())) return false;
        return !animal.isVehicle() && !animal.isPassenger();
    }

    private void eat(ItemEntity food) {
        ItemStack itemStack = food.getItem();
        animal.onItemPickup(food);
        animal.take(food, 1);
        itemStack.shrink(1);
        if (itemStack.isEmpty()) food.discard();
        animal.setInLove(food.getOwner() instanceof Player player ? player : null);
    }

    private boolean hasUnclaimedFood(ItemEntity food) {
        Set<Animal> claimants = CLAIMS.get(food);
        if (claimants == null) return true;
        claimants.removeIf(claimant -> !claimant.isAlive());
        return claimants.size() < food.getItem().getCount();
    }

    @SuppressWarnings("resource")
    private void markUnreachable(ItemEntity food) {
        unreachable.put(food, animal.level().getGameTime() + 600);
    }
}
