package guichaguri.dohablocker.forge;

import guichaguri.dohablocker.BlockerManager;
import java.io.File;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerConnectionFromClientEvent;

/**
 * @author Guilherme Chaguri
 */
@Mod(
        modid = "doha-blocker",
        name = "DOHA-Blocker",
        version = BlockerManager.VERSION,
        acceptableRemoteVersions = "*"
)
public class BlockerForge {

    @EventHandler
    public void init(FMLPreInitializationEvent event) {
        // Registers the event
        MinecraftForge.EVENT_BUS.register(this);

        // Loads the config
        BlockerManager.loadConfig(new File(event.getModConfigurationDirectory(), "config.json"));
    }

    @SubscribeEvent
    public void login(ServerConnectionFromClientEvent event) {
        if(event.isLocal()) return;

        INetHandlerPlayServer playServer = event.getHandler();
        if(!(playServer instanceof NetHandlerPlayServer)) return;

        EntityPlayerMP player = ((NetHandlerPlayServer)playServer).playerEntity;
        if(BlockerManager.isBlocked(player.getGameProfile().getId())) {
            player.connection.disconnect(BlockerManager.MESSAGE);
        }
    }

}
