# BOHA - Blocker of Hacked Accounts
A Forge/LiteLoader/Vanilla/Sponge/Bukkit/BungeeCord plugin that blocks hacked accounts using the [Database of Hacked Accounts](https://github.com/IamBlueSlime/DOHA) (DOHA)

## Downloads
Downloads can be found in the [Releases](https://github.com/Guichaguri/BOHA/releases) page.
* `BOHA-Mod-{mcversion}-{version}.jar` - BOHA for Forge/LiteLoader/Vanilla
* `BOHA-Plugin-{version}.jar` - BOHA for Sponge/Bukkit/BungeeCord

Note: If you're using SpongeForge, please, use the plugin instead of the mod. It's safer and not version-dependent.

## Installation
### Bukkit/BungeeCord
Put the plugin file in the `plugins` folder
### Forge/LiteLoader/Sponge
Put the mod/plugin file in the `mods` folder.
### Vanilla
1. Add the mod, [LegacyLauncher](https://github.com/Mojang/LegacyLauncher) and [ASM](http://asm.ow2.org/) to the classpath.
2. Set the main class to `net.minecraft.launchwrapper.Launch`
3. Add `--tweakClass guichaguri.boha.tweak.BlockerTweak` to the start command

## Configuration
For both the mod and the plugin, the configuration should look like this:
```json
{
  "message": "&cYou are using a hacked account.",
  "cache": {
    "enabled": true,
    "timeout": 3600
  },
  "database": {
    "enabled": true,
    "interval": 24
  }
}
```
* `message` is the message which will be used for kicking the hacked account. You can use formatting codes.
* `cache` - Cache requests to the DOHA API
  * `enabled` is whether caching will be enabled.
  * `timeout` is the interval in seconds which the cache will be cleared.
* `database` - The whole DOHA database will be downloaded locally
  * `enabled` is whether the database will be downloaded and used
  * `interval` is the minimum time in hours to refresh the database

Using database is recommended because it not only makes the verification faster, but also prevents hacked accounts from joining your server when the DOHA API server is down.

## Credits
* [IamBlueSlime](https://github.com/IamBlueSlime) for DOHA