package guichaguri.boha.vanilla;

import com.mojang.authlib.GameProfile;
import guichaguri.boha.Blocker;
import guichaguri.boha.BlockerManager;
import java.io.File;
import java.util.UUID;
import net.minecraft.server.network.NetHandlerLoginServer;
import net.minecraft.util.text.TextComponentString;

/**
 * @author Guilherme Chaguri
 */
public class BlockerHooks {

    static {
        BlockerManager.loadConfig(new File("config", "boha.json"), new File("data", "doha-database.json"));
    }

    /**
     * Called from {@link NetHandlerLoginServer#tryAcceptPlayer()}
     */
    public static boolean isBlocked(NetHandlerLoginServer login, GameProfile profile) {
        if(profile == null) return false;

        UUID uuid = profile.getId();
        if(uuid == null) return false;

        if(Blocker.isBlocked(uuid)) {
            login.func_194026_b(new TextComponentString(Blocker.MESSAGE));
            return true;
        }
        return false;
    }

}
