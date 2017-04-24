package guichaguri.dohablocker.bukkit;

import guichaguri.dohablocker.BlockerManager;
import java.io.File;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Guilherme Chaguri
 */
public class BlockerBukkit extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Registers the event
        getServer().getPluginManager().registerEvents(this, this);

        // Loads the config
        BlockerManager.loadConfig(new File(getDataFolder(), "config.json"));
    }

    @EventHandler
    public void login(AsyncPlayerPreLoginEvent event) {
        if(BlockerManager.isBlocked(event.getUniqueId())) {
            event.disallow(Result.KICK_OTHER, BlockerManager.MESSAGE);
        }
    }
}
