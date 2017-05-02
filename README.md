# BOHA - Blocker of Hacked Accounts
A Forge/LiteLoader/Vanilla/Sponge/Bukkit/BungeeCord plugin that blocks hacked accounts using the [Database of Hacked Accounts](https://github.com/IamBlueSlime/DOHA) (DOHA)

## Downloads
Downloads can be found in the [Releases](https://github.com/Guichaguri/BOHA/releases) page.
* `BOHA-Mod-{mcversion}-{version}.jar` - BOHA for Forge/LiteLoader/Vanilla
* `BOHA-Plugin-{version}.jar` - BOHA for Sponge/Bukkit/BungeeCord

Note: If you're using SpongeForge, please, use the plugin instead of the mod. It's safer and not version-dependent.

## Installation
### Bukkit/BungeeCord
Drop the plugin in the `plugins` folder
### Forge/LiteLoader/Sponge
Drop the mod/plugin in the `mods` folder.
### Vanilla
1. Add the mod, LaunchWrapper and ASM to the classpath.
2. Set the main class to `net.minecraft.launchwrapper.Launch`
3. Add `--tweakClass guichaguri.boha.tweak.BlockerTweak` to the start command

## Configuration
For both the mod and the plugin, the configuration should look like this:
```json
{
  "message": "&cYou are using a hacked account.",
  "cacheTimeout": 3600
}
```
`message` is the message which will be used for kicking the hacked account. You can use formatting codes.

`cacheTimeout` is the interval in seconds which the cache will be cleared. Set it to 0 to disable caching.

## Credits
* [IamBlueSlime](https://github.com/IamBlueSlime) for DOHA