package org.csits.platform.component.cache.redis.springdata;

import com.alicp.jetcache.external.ExternalCacheBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * Created on 2019/4/21.
 *
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public class RedisSpringDataCacheBuilder<T extends ExternalCacheBuilder<T>> extends ExternalCacheBuilder<T> {
    protected RedisSpringDataCacheBuilder() {
        buildFunc(config -> new RedisSpringDataCache((RedisSpringDataCacheConfig) config));
    }

    public static RedisSpringDataCacheBuilderImpl createBuilder() {
        return new RedisSpringDataCacheBuilderImpl();
    }

    @Override
    public RedisSpringDataCacheConfig getConfig() {
        if (config == null) {
            config = new RedisSpringDataCacheConfig();
        }
        return (RedisSpringDataCacheConfig) config;
    }

    public T connectionFactory(RedisConnectionFactory connectionFactory) {
        getConfig().setConnectionFactory(connectionFactory);
        return self();
    }

    public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
        getConfig().setConnectionFactory(connectionFactory);
    }

    public static class RedisSpringDataCacheBuilderImpl extends RedisSpringDataCacheBuilder<RedisSpringDataCacheBuilderImpl> {
    }

}

