package guichaguri.dohablocker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static String MESSAGE = Blocker.DEFAULT_MSG;

    public static void loadConfig(File configFile) {
        Config config;
        try {
            config = gson.fromJson(new FileReader(configFile), Config.class);
        } catch(Exception ex) {
            config = new Config();
        }

        MESSAGE = config.message;

        try {
            if(!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            }

            gson.toJson(config, new FileWriter(configFile));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private static boolean check(UUID uuid) throws IOException {
        URL url = Blocker.getUrl(uuid);

        InputStreamReader reader = new InputStreamReader(url.openStream());
        Check check = gson.fromJson(reader, Check.class);

        return check.exists;
    }

    public static boolean isBlocked(UUID uuid) {
        try {
            return check(uuid);
        } catch(Exception ex) {
            return false;
        }
    }

    private static class Config {
        public String message = Blocker.DEFAULT_MSG;
    }

    private static class Check {
        public boolean exists = false;
    }

}
