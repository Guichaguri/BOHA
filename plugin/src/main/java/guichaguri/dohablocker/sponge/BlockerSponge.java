package guichaguri.dohablocker.sponge;

import com.google.inject.Inject;
import guichaguri.dohablocker.Blocker;
import guichaguri.dohablocker.BlockerManager;
import java.io.File;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent.Auth;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

/**
 * @author Guilherme Chaguri
 */
@Plugin(
        id = "doha-blocker-sponge",
        name = "DOHA-Blocker",
        version = Blocker.VERSION,
        authors = "Guichaguri",
        description = "Blocks connections from hacked accounts"
)
public class BlockerSponge {

    @Inject
    @ConfigDir(sharedRoot = false)
    private File configDir;

    @Listener
    public void init(GameStartingServerEvent event) {
        // Loads the config
        BlockerManager.loadConfig(new File(configDir, "config.json"));
    }

    @Listener
    public void login(Auth event) {
        if(BlockerManager.isBlocked(event.getProfile().getUniqueId())) {
            event.setMessage(Text.builder(BlockerManager.MESSAGE).build());
            event.setCancelled(true);
        }
    }

}
