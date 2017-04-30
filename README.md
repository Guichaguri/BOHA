# DOHA-Blocker
A Forge/LiteLoader/Vanilla/Sponge/Bukkit/BungeeCord plugin that blocks hacked accounts using the [Database of Hacked Accounts](https://github.com/IamBlueSlime/DOHA) (DOHA)

## Downloads
Downloads can be found in the [Releases](https://github.com/Guichaguri/DOHA-Blocker/releases) page.
* `DOHA-Blocker-Mod-{mcversion}-{version}.jar` - DOHA-Blocker for Forge/LiteLoader/Vanilla
* `DOHA-Blocker-Plugin-{version}.jar` - DOHA-Blocker for Sponge/Bukkit/BungeeCord

Note: If you're using SpongeForge, please, use the plugin instead of the mod. It's safer and not version-dependent.

## Installation
### Bukkit/BungeeCord
Drop the plugin in the `plugins` folder
### Forge/LiteLoader/Sponge
Drop the mod/plugin in the `mods` folder.
### Vanilla
1. Add the mod, LaunchWrapper and ASM to the classpath.
2. Set the main class to `net.minecraft.launchwrapper.Launch`
3. Add `--tweakClass guichaguri.dohablocker.tweak.BlockerTweak` to the start command

## Credits
* [IamBlueSlime](https://github.com/IamBlueSlime) for DOHA