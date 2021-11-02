package org.csits.platform.autoconfigure.cache.properties;

import com.alicp.jetcache.anno.CacheConsts;

public class LocalAreaCacheConfig extends GenericAreaCacheConfig {

    private String limit = String.valueOf(CacheConsts.DEFAULT_LOCAL_LIMIT);

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
