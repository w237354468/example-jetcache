package org.csits.platform.autoconfigure.cache.configure;

import com.alicp.jetcache.AbstractCacheBuilder;
import com.alicp.jetcache.CacheBuilder;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import org.csits.platform.autoconfigure.cache.properties.AreaCache;
import org.csits.platform.autoconfigure.cache.properties.CsitsCacheProperties;
import org.csits.platform.autoconfigure.cache.wrapper.FunctionWrapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public abstract class AbstractCacheAutoInit implements InitializingBean {

    @Autowired
    private CsitsCacheProperties cacheProperties;

    @Autowired
    protected SpringConfigProvider springConfigProvider;

    protected String typeName;

    private boolean inited = false;

    public AbstractCacheAutoInit(String cacheType) {
        Objects.requireNonNull(cacheType, "cacheType can't be null");
        this.typeName = cacheType;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!inited) {
            synchronized (this) {
                if (!inited) {
                    initCache(cacheProperties);
                    inited = true;
                }
            }
        }
    }

    protected void parseGeneralConfig(CacheBuilder builder, AreaCache ac) {

        AbstractCacheBuilder acb = (AbstractCacheBuilder) builder;
        acb.keyConvertor(new FunctionWrapper<>(() -> springConfigProvider.parseKeyConvertor(ac.getKeyConvertor())));

        acb.setExpireAfterAccessInMillis(ac.getExpireAfterAccessInMillis());
        acb.setExpireAfterWriteInMillis(ac.getExpireAfterWriteInMillis());
    }

    protected abstract void initCache(CsitsCacheProperties cacheProperties);
}
