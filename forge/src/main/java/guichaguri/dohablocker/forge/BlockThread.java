package guichaguri.dohablocker.forge;

import guichaguri.dohablocker.BlockerManager;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * @author Guilherme Chaguri
 */
public class BlockThread extends Thread {

    private final EntityPlayerMP player;

    public BlockThread(EntityPlayerMP player) {
        this.player = player;
    }

    @Override
    public void run() {
        if(BlockerManager.isBlocked(player.getGameProfile().getId())) {
            if(player.connection != null) {
                player.connection.disconnect(BlockerManager.MESSAGE);
            }
        }
    }
}
