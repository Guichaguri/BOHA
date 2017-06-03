package guichaguri.boha;

import guichaguri.boha.logic.DohaAPIChecker;
import guichaguri.boha.logic.DohaDatabaseChecker;
import guichaguri.boha.logic.GongAPIChecker;
import guichaguri.boha.logic.IChecker;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Guilherme Chaguri
 */
public class BlockerManager {

    public static void loadConfig(File configFile, File databaseFile) {
        Config config;
        FileReader reader = null;
        try {
            reader = new FileReader(configFile);
            config = Blocker.GSON.fromJson(reader, Config.class);
        } catch(Exception ex) {
            config = null;
        } finally {
            Blocker.closeQuietly(reader);
        }

        if(config == null) config = new Config();

        FileWriter writer = null;
        try {
            configFile.getParentFile().mkdirs();
            writer = new FileWriter(configFile);
            Blocker.GSON.toJson(config, writer);
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            Blocker.closeQuietly(writer);
        }


        Blocker.MESSAGE = Blocker.translateChatColors(config.message);

        List<IChecker> checkers = new ArrayList<IChecker>();

        if(config.gong.enabled) {
            checkers.add(new GongAPIChecker(config.gong.cache, config.gong.timeout));
        }

        if(config.doha.enabled) {
            checkers.add(new DohaAPIChecker(config.doha.cache, config.doha.timeout));
        }

        if(config.doha_database.enabled) {
            checkers.add(new DohaDatabaseChecker(databaseFile, config.doha_database.refresh));
        }

        Blocker.CHECKER = checkers.toArray(new IChecker[checkers.size()]);
    }

    private static class Config {
        String message = Blocker.DEFAULT_MSG;
        BlockerAPI gong = new BlockerAPI();
        BlockerAPI doha = new BlockerAPI();
        BlockerDatabase doha_database = new BlockerDatabase();
    }

    private static class BlockerAPI {
        boolean enabled = true;
        boolean cache = true;
        int timeout = Blocker.DEFAULT_CACHE_TIMEOUT;
    }

    private static class BlockerDatabase {
        boolean enabled = true;
        int refresh = Blocker.DEFAULT_DB_INTERVAL;
    }

}
