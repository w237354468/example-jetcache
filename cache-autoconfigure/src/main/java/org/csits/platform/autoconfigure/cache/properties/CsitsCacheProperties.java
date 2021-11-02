package org.csits.platform.autoconfigure.cache.properties;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("csits.cache")
public class CsitsCacheProperties {

    private String[] hiddenPackages;
    private int statIntervalMinutes;
    private boolean areaInCacheName = true;
    private boolean penetrationProtect = false;
    private boolean enableMethodCache = true;

    private Map<String, LocalAreaCacheConfig> local;
    private Map<String, RedisAreaCacheConfig> remote;

    public CsitsCacheProperties() {
    }

    public String[] getHiddenPackages() {
        return hiddenPackages;
    }

    public void setHiddenPackages(String[] hiddenPackages) {
        this.hiddenPackages = hiddenPackages;
    }

    public void setHidePackages(String[] hidePackages) {
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

    public Map<String, LocalAreaCacheConfig> getLocal() {
        return local;
    }

    public void setLocal(
        Map<String, LocalAreaCacheConfig> local) {
        this.local = local;
    }

    public Map<String, RedisAreaCacheConfig> getRemote() {
        return remote;
    }

    public void setRemote(Map<String, RedisAreaCacheConfig> remote) {
        this.remote = remote;
    }

}
