package guichaguri.boha.vanilla;

import guichaguri.boha.Blocker;
import guichaguri.boha.BlockerManager;
import java.io.File;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.server.network.NetHandlerLoginServer;

/**
 * @author Guilherme Chaguri
 */
public class BlockerHooks {

    static {
        BlockerManager.loadConfig(new File("config", "boha.json"));
    }

    /**
     * Called from {@link NetHandlerLoginServer#processLoginStart(CPacketLoginStart)}
     */
    public static boolean isBlocked(NetHandlerLoginServer login, CPacketLoginStart packet) {
        boolean blocked = BlockerManager.isBlocked(packet.getProfile().getId());
        if(blocked) login.closeConnection(Blocker.MESSAGE);
        return blocked;
    }

}
