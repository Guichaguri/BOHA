package guichaguri.boha.sponge;

import com.google.inject.Inject;
import guichaguri.boha.Blocker;
import guichaguri.boha.BlockerManager;
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
        id = "boha",
        name = "BOHA",
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
        BlockerManager.loadConfig(new File(configDir, "config.json"), new File(configDir, "doha-database.json"));
    }

    @Listener
    public void login(Auth event) {
        if(Blocker.isBlocked(event.getProfile().getUniqueId())) {
            event.setMessage(Text.of(Blocker.MESSAGE));
            event.setCancelled(true);
        }
    }

}
