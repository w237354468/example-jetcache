package org.csits.platform.autoconfigure.cache;

import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import java.util.function.Function;
import org.csits.platform.autoconfigure.cache.configure.AutoConfigureBeans;
import org.csits.platform.autoconfigure.cache.configure.LocalCaffeineAutoConfiguration;
import org.csits.platform.autoconfigure.cache.configure.RemoteRedisCacheAutoConfiguration;
import org.csits.platform.autoconfigure.cache.convertor.JacksonKeyConvertor;
import org.csits.platform.autoconfigure.cache.properties.CsitsCacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(GlobalCacheConfig.class)
@ConditionalOnMissingBean(GlobalCacheConfig.class)
@EnableConfigurationProperties(CsitsCacheProperties.class)
@Import({
    LocalCaffeineAutoConfiguration.class,
    RemoteRedisCacheAutoConfiguration.class
})
public class CsitsCacheAutoConfiguration {

    public static final String GLOBAL_CACHE_CONFIG_NAME = "globalCacheConfig";

    @Bean
    public static BeanDependencyManager beanDependencyManager() {
        return new BeanDependencyManager();
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringConfigProvider springConfigProvider() {
        return new SpringConfigProvider();
    }

    @Bean(name = GLOBAL_CACHE_CONFIG_NAME)
    public GlobalCacheConfig globalCacheConfig(CsitsCacheProperties cacheProperties) {
        GlobalCacheConfig globalCacheConfig = new GlobalCacheConfig();
        globalCacheConfig.setHiddenPackages(cacheProperties.getHiddenPackages());
        globalCacheConfig.setStatIntervalMinutes(cacheProperties.getStatIntervalMinutes());
        globalCacheConfig.setAreaInCacheName(cacheProperties.isAreaInCacheName());
        globalCacheConfig.setPenetrationProtect(cacheProperties.isPenetrationProtect());
        globalCacheConfig.setEnableMethodCache(cacheProperties.isEnableMethodCache());
        globalCacheConfig.setLocalCacheBuilders(AutoConfigureBeans.LOCAL_CACHE_BUILDERS);
        globalCacheConfig.setRemoteCacheBuilders(AutoConfigureBeans.REMOTE_CACHE_BUILDERS);
        return globalCacheConfig;
    }

    @Bean("jacksonKeyConvertor")
    public Function<Object, Object> customerKeyConvertor() {
        return JacksonKeyConvertor.INSTANCE;
    }
}
