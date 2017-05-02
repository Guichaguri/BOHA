package guichaguri.boha.logic;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author Guilherme Chaguri
 */
public abstract class ArrayChecker implements IChecker {

    protected UUID[] database;

    protected final boolean refreshEnabled;
    protected final long refreshInterval;
    protected long refreshTime = 0;

    protected ArrayChecker(int refreshInterval) {
        this.refreshEnabled = refreshInterval > 0;
        this.refreshInterval = refreshInterval * 1000L * 3600L;
    }

    public boolean hasData() {
        return database != null && database.length != 0;
    }

    @Override
    public boolean isBlocked(UUID uuid) {
        if(refreshEnabled) {
            long now = System.currentTimeMillis();
            if(now - refreshTime > refreshInterval) {
                UUID[] db = refresh();
                if(db != null) {
                    database = db;
                    refreshTime = now;
                }
            }
        }

        return Arrays.binarySearch(database, uuid) >= 0;
    }

    public abstract UUID[] refresh();

}
