package org.csits.platform.autoconfigure.cache.properties;


import com.alicp.jetcache.anno.CacheConsts;

public class GenericAreaCacheConfig {

    protected String type;
    protected String keyConvertor = "bean:jacksonKeyConvertor";
    protected Integer expireAfterWriteInMillis = CacheConsts.DEFAULT_EXPIRE;
    protected Integer expireAfterAccessInMillis = 0;

    public String getKeyConvertor() {
        return keyConvertor;
    }

    public void setKeyConvertor(String keyConvertor) {
        this.keyConvertor = keyConvertor;
    }

    public Integer getExpireAfterWriteInMillis() {
        return expireAfterWriteInMillis;
    }

    public void setExpireAfterWriteInMillis(Integer expireAfterWriteInMillis) {
        this.expireAfterWriteInMillis = expireAfterWriteInMillis;
    }

    public Integer getExpireAfterAccessInMillis() {
        return expireAfterAccessInMillis;
    }

    public void setExpireAfterAccessInMillis(Integer expireAfterAccessInMillis) {
        this.expireAfterAccessInMillis = expireAfterAccessInMillis;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}