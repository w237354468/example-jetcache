package org.csits.platform.autoconfigure.cache.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("csits.cache")
public class CsitsCacheProperties {

    private String[] hiddenPackages;
    private int statIntervalMinutes;
    private boolean areaInCacheName = true;
    private boolean penetrationProtect = false;
    private boolean enableMethodCache = true;

    private Map<String, LocalAreaCache> local;
    private Map<String, RedisAreaCache> remote;

    public CsitsCacheProperties() {
    }

    public String[] getHiddenPackages() {
        // keep same with GlobalCacheConfig
        return hiddenPackages;
    }

    public void setHiddenPackages(String[] hiddenPackages) {
        // keep same with GlobalCacheConfig
        this.hiddenPackages = hiddenPackages;
    }

    public void setHidePackages(String[] hidePackages) {
        // keep same with GlobalCacheConfig
        this.hiddenPackages = hidePackages;
    }

    public int getStatIntervalMinutes() {
        return statIntervalMinutes;
    }

    public void setStatIntervalMinutes(int statIntervalMinutes) {
        this.statIntervalMinutes = statIntervalMinutes;
    }

    public boolean isAreaInCacheName() {
        return areaInCacheName;
    }

    public void setAreaInCacheName(boolean areaInCacheName) {
        this.areaInCacheName = areaInCacheName;
    }

    public boolean isPenetrationProtect() {
        return penetrationProtect;
    }

    public void setPenetrationProtect(boolean penetrationProtect) {
        this.penetrationProtect = penetrationProtect;
    }

    public boolean isEnableMethodCache() {
        return enableMethodCache;
    }

    public void setEnableMethodCache(boolean enableMethodCache) {
        this.enableMethodCache = enableMethodCache;
    }

    public Map<String, LocalAreaCache> getLocal() {
        return local;
    }

    public void setLocal(
            Map<String, LocalAreaCache> local) {
        this.local = local;
    }

    public Map<String, RedisAreaCache> getRemote() {
        return remote;
    }

    public void setRemote(Map<String, RedisAreaCache> remote) {
        this.remote = remote;
    }

}
