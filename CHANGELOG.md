- **All client features can now be configured from Sodium video settings**. This is optional - these features can still be configured from Mod Menu as usual.
- Added new features:

**Per-dimension brightness** (client feature)
- Allows configuring brightness for each dimension individually.
- The config entry for the dimension will be added the first time the dimension is visited.
- The brightness of the current dimension can be changed at any time in Sodium video settings.

**Raise hotbar** (client feature)
- Raises the hotbar from the bottom of the screen, much like how it is in Bedrock Edition.
- Hotbar is raised by 2 pixels by default, can be configured up to 24 pixels.
- Supports UI resource packs by dynamically patching the hotbar selector sprite on resource reload.
- Feature will be disabled if Raised is installed.

**Stable block drops** (server feature)
- Disables the random horizontal movement that block drops get when breaking blocks.
- Makes block drops always spawn in the center of the block.
- Crouching while breaking a block near you will make its drops fling towards you.
- Each part of the feature is configurable.
