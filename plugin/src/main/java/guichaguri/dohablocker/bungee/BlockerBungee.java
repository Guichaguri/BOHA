package guichaguri.dohablocker.bungee;

import guichaguri.dohablocker.BlockerManager;
import java.io.File;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

/**
 * @author Guilherme Chaguri
 */
public class BlockerBungee extends Plugin implements Listener {

    @Override
    public void onEnable() {
        // Registers the event
        getProxy().getPluginManager().registerListener(this, this);

        // Loads the config
        BlockerManager.loadConfig(new File(getDataFolder(), "config.json"));
    }

    @EventHandler
    public void login(PreLoginEvent event) {
        if(BlockerManager.isBlocked(event.getConnection().getUniqueId())) {
            event.setCancelReason(BlockerManager.MESSAGE);
            event.setCancelled(true);
        }
    }

}
