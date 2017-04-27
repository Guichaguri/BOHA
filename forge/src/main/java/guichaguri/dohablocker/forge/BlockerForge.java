package guichaguri.dohablocker.forge;

import guichaguri.dohablocker.Blocker;
import guichaguri.dohablocker.BlockerManager;
import java.io.File;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerConnectionFromClientEvent;

/**
 * @author Guilherme Chaguri
 */
@Mod(
        modid = "doha-blocker-forge",
        name = "DOHA-Blocker",
        version = Blocker.VERSION,
        acceptableRemoteVersions = "*"
)
public class BlockerForge {

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Registers the event
        MinecraftForge.EVENT_BUS.register(this);

        // Loads the config
        BlockerManager.loadConfig(new File(event.getModConfigurationDirectory(), "config.json"));
    }

    @EventHandler
    public void init(FMLPostInitializationEvent event) {
        // Sponge is loaded. We'll warn the user.
        if(Loader.isModLoaded("sponge") || Loader.isModLoaded("spongeapi")) {
            System.out.println("--------------- DOHA-Blocker ---------------");
            System.out.println("You're using the Forge version with Sponge");
            System.out.println("Please, use DOHA-Blocker for Sponge instead");
            System.out.println("--------------------------------------------");
        }
    }

    @SubscribeEvent
    public void login(ServerConnectionFromClientEvent event) {
        if(event.isLocal()) return;

        INetHandlerPlayServer playServer = event.getHandler();
        if(!(playServer instanceof NetHandlerPlayServer)) return;

        EntityPlayerMP player = ((NetHandlerPlayServer)playServer).playerEntity;

        BlockThread thread = new BlockThread(player);
        thread.start();
    }

}
