package org.csits.platform.autoconfigure.cache.configure;

import static com.alicp.jetcache.anno.CacheConsts.DEFAULT_AREA;

import com.alicp.jetcache.CacheBuilder;
import com.alicp.jetcache.CacheConfigException;
import com.alicp.jetcache.external.ExternalCacheBuilder;
import java.util.HashMap;
import java.util.Map;
import org.csits.platform.autoconfigure.cache.condition.CsitsCacheCondition;
import org.csits.platform.autoconfigure.cache.properties.CsitsCacheProperties;
import org.csits.platform.autoconfigure.cache.properties.RedisAreaCacheConfig;
import org.csits.platform.autoconfigure.cache.wrapper.FunctionWrapper;
import org.csits.platform.component.cache.redis.springdata.RedisSpringDataCacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@Conditional(RemoteRedisCacheAutoConfiguration.SpringDataRedisCacheCondition.class)
public class RemoteRedisCacheAutoConfiguration {

    @Bean
    public SpringDataRedisAutoInit springDataRedisAutoInit() {
        return new SpringDataRedisAutoInit();
    }

    public static class SpringDataRedisAutoInit extends AbstractCacheAutoInit implements
        ApplicationContextAware {

        private static final Logger logger = LoggerFactory.getLogger(
            RemoteRedisCacheAutoConfiguration.class);

        private ApplicationContext applicationContext;

        public SpringDataRedisAutoInit() {
            super("redis");
        }

        @Override
        protected void initCache(CsitsCacheProperties properties) {
            Map<String, RedisAreaCacheConfig> remoteCacheAreas = properties.getRemote();

            if (remoteCacheAreas == null) {
                remoteCacheAreas = new HashMap<>();
            }

            // 加载默认default
            if (remoteCacheAreas.isEmpty() || !remoteCacheAreas.containsKey(DEFAULT_AREA)) {
                remoteCacheAreas.put(DEFAULT_AREA, new RedisAreaCacheConfig());
            }

            for (String areaKey : remoteCacheAreas.keySet()) {
                RedisAreaCacheConfig areaCache = remoteCacheAreas.get(areaKey);
                if (!areaCache.getType().equals(typeName)) {
                    continue;
                }

                RedisConnectionFactory factory = getRedisConnectionFactory(areaCache);
                ExternalCacheBuilder builder = RedisSpringDataCacheBuilder.createBuilder()
                    .connectionFactory(factory);
                parseGeneralConfig(builder, remoteCacheAreas.get(areaKey));
                AutoConfigureBeans.REMOTE_CACHE_BUILDERS.put(areaKey, builder);

                logger.info("init cache area {} , type= {}", areaKey, "redis");
            }
        }

        protected void parseGeneralConfig(CacheBuilder builder, RedisAreaCacheConfig ac) {
            super.parseGeneralConfig(builder, ac);

            RedisSpringDataCacheBuilder acb = (RedisSpringDataCacheBuilder) builder;

            acb.setValueEncoder(new FunctionWrapper<>(
                () -> springConfigProvider.parseValueEncoder((ac.getValueEncoder()))));
            acb.setValueDecoder(new FunctionWrapper<>(
                () -> springConfigProvider.parseValueDecoder(ac.getValueDecoder())));
            acb.setKeyPrefix(ac.getKeyPrefix());
        }

        public RedisConnectionFactory getRedisConnectionFactory(
            RedisAreaCacheConfig areaCache) {
            Map<String, RedisConnectionFactory> beans = applicationContext.getBeansOfType(
                RedisConnectionFactory.class);
            if (beans == null || beans.isEmpty()) {
                throw new CacheConfigException("no RedisConnectionFactory in spring context");
            }

            RedisConnectionFactory factory = beans.values().iterator().next();
            if (beans.size() > 1) {
                String connectionFactoryName = areaCache.getConnectionFactory();
                if (connectionFactoryName == null) {
                    throw new CacheConfigException(
                        "connectionFactory is required, because there is multiple RedisConnectionFactory in Spring context");
                }
                if (!beans.containsKey(connectionFactoryName)) {
                    throw new CacheConfigException("there is no RedisConnectionFactory named "
                        + connectionFactoryName + " in Spring context");
                }
                factory = beans.get(connectionFactoryName);
            }
            return factory;
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
            this.applicationContext = applicationContext;
        }
    }

    public static class SpringDataRedisCacheCondition extends CsitsCacheCondition {

        public SpringDataRedisCacheCondition() {
            super("redis");
        }
    }
}
