package guichaguri.dohablocker;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

/**
 * @author Guilherme Chaguri
 */
public class BlockerManager {

    public static String MESSAGE = Blocker.DEFAULT_MSG;

    private static JsonObject createConfig(File configFile) {
        JsonObject config = Json.object();
        config.add("message", Blocker.DEFAULT_MSG);
        try {
            if(!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            }
            config.writeTo(new FileWriter(configFile));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return config;
    }

    public static void loadConfig(File configFile) {
        JsonObject config;
        try {
            config = Json.parse(new FileReader(configFile)).asObject();
        } catch(Exception ex) {
            config = createConfig(configFile);
        }

        MESSAGE = config.getString("message", Blocker.DEFAULT_MSG);
    }

    private static boolean check(UUID uuid) throws IOException {
        URL url = Blocker.getUrl(uuid);

        InputStreamReader reader = new InputStreamReader(url.openStream());
        JsonObject data = Json.parse(reader).asObject();

        return data.getBoolean("exists", false);
    }

    public static boolean isBlocked(UUID uuid) {
        try {
            return check(uuid);
        } catch(Exception ex) {
            return false;
        }
    }

}
