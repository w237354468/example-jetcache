package org.csits.platform.autoconfigure.cache.properties;


import com.alicp.jetcache.RefreshPolicy;
import com.alicp.jetcache.anno.CacheConsts;

public class AreaCache {

    protected String type;
    protected String keyConvertor = "bean:jacksonKeyConvertor";
    protected Long expireAfterWriteInMillis = (long) CacheConsts.DEFAULT_EXPIRE;
    protected Long expireAfterAccessInMillis = 0L;

    public String getKeyConvertor() {
        return keyConvertor;
    }

    public void setKeyConvertor(String keyConvertor) {
        this.keyConvertor = keyConvertor;
    }

    public Long getExpireAfterWriteInMillis() {
        return expireAfterWriteInMillis;
    }

    public void setExpireAfterWriteInMillis(Long expireAfterWriteInMillis) {
        this.expireAfterWriteInMillis = expireAfterWriteInMillis;
    }

    public Long getExpireAfterAccessInMillis() {
        return expireAfterAccessInMillis;
    }

    public void setExpireAfterAccessInMillis(Long expireAfterAccessInMillis) {
        this.expireAfterAccessInMillis = expireAfterAccessInMillis;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}