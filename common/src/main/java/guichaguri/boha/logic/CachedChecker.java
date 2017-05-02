package guichaguri.boha.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Guilherme Chaguri
 */
public abstract class CachedChecker implements IChecker {

    protected final Map<UUID, Boolean> cache = new HashMap<UUID, Boolean>();

    protected final boolean cacheEnabled;
    protected final long cacheTimeout;
    protected long cacheTime = 0;

    protected CachedChecker(boolean cacheEnabled, int cacheTimeout) {
        this.cacheEnabled = cacheEnabled && cacheTimeout > 0;
        this.cacheTimeout = cacheTimeout * 1000L;
    }

    @Override
    public boolean isBlocked(UUID uuid) {
        // Check if caching is disabled
        if(!cacheEnabled) return check(uuid);

        Boolean blocked;

        // Check if we should clear the cache
        long currentTime = System.currentTimeMillis();
        if(currentTime > cacheTime + cacheTimeout) {
            cache.clear();
            cacheTime = currentTime;
            blocked = null;
        } else {
            blocked = cache.get(uuid);
        }

        // Check if not cached yet
        if(blocked == null) {
            blocked = check(uuid);
            cache.put(uuid, blocked);
        }

        return blocked;
    }

    public abstract boolean check(UUID uuid);

}
