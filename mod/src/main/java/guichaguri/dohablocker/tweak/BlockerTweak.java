package guichaguri.dohablocker.tweak;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

/**
 * @author Guilherme Chaguri
 */
public class BlockerTweak implements ITweaker {

    private List<String> args;

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.args = new ArrayList<String>(args);

        this.args.add("--version");
        this.args.add(profile);

        if(assetsDir != null) {
            this.args.add("--assetsDir");
            this.args.add(assetsDir.getAbsolutePath());
        }

        if(gameDir != null) {
            this.args.add("--gameDir");
            this.args.add(gameDir.getAbsolutePath());
        }
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader loader) {
        loader.addTransformerExclusion("guichaguri.dohablocker.tweak");
        loader.registerTransformer("guichaguri.dohablocker.tweak.transformer.BlockTransformer");
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.server.MinecraftServer";
    }

    @Override
    public String[] getLaunchArguments() {
        ArrayList args = (ArrayList)Launch.blackboard.get("ArgumentList");
        if(args.isEmpty()) args.addAll(this.args);

        this.args = null;

        return new String[0];
    }
}
