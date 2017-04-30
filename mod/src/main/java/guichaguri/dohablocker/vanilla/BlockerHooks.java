package guichaguri.dohablocker.vanilla;

import guichaguri.dohablocker.Blocker;
import guichaguri.dohablocker.BlockerManager;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.server.network.NetHandlerLoginServer;

/**
 * @author Guilherme Chaguri
 */
public class BlockerHooks {

    public static boolean isBlocked(NetHandlerLoginServer login, CPacketLoginStart packet) {
        boolean blocked = BlockerManager.isBlocked(packet.getProfile().getId());
        if(blocked) login.closeConnection(Blocker.MESSAGE);
        return blocked;
    }

}
