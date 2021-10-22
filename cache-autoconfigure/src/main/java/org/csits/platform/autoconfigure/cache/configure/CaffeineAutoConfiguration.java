package org.csits.platform.autoconfigure.cache.configure;

import com.alicp.jetcache.CacheBuilder;
import com.alicp.jetcache.embedded.CaffeineCacheBuilder;
import org.csits.platform.autoconfigure.cache.condition.CsitsCacheCondition;
import org.csits.platform.autoconfigure.cache.properties.CsitsCacheProperties;
import org.csits.platform.autoconfigure.cache.properties.LocalAreaCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.HashMap;
import java.util.Map;

import static com.alicp.jetcache.anno.CacheConsts.DEFAULT_AREA;

@Configuration
@Conditional(CaffeineAutoConfiguration.LocalCacheCondition.class)
public class CaffeineAutoConfiguration {

    @Bean
    public CaffeineAutoInit caffeineAutoInit() {
        return new CaffeineAutoInit();
    }

    public static class CaffeineAutoInit extends AbstractCacheAutoInit {
        private static final Logger logger = LoggerFactory.getLogger(
                CaffeineAutoConfiguration.class);

        public CaffeineAutoInit() {
            super("caffeine");
        }

        @Override
        protected void initCache(CsitsCacheProperties properties) {
            Map<String, LocalAreaCache> localCacheAreas = properties.getLocal();

            if (localCacheAreas == null) {
                localCacheAreas = new HashMap<>();
            }

            // 加载默认default
            if (localCacheAreas.isEmpty() || !localCacheAreas.containsKey(DEFAULT_AREA)) {
                localCacheAreas.put(DEFAULT_AREA, new LocalAreaCache());
            }

            for (String areaKey : localCacheAreas.keySet()) {

                CaffeineCacheBuilder builder = CaffeineCacheBuilder.createCaffeineCacheBuilder();
                parseGeneralConfig(builder, localCacheAreas.get(areaKey));
                AutoConfigureBeans.LOCAL_CACHE_BUILDERS.put(areaKey, builder);

                logger.info("init cache area {} , type= {}", areaKey, "caffeine");
            }
        }

        protected void parseGeneralConfig(CacheBuilder builder, LocalAreaCache ac) {

            super.parseGeneralConfig(builder, ac);
            CaffeineCacheBuilder acb = (CaffeineCacheBuilder) builder;

            acb.limit(Integer.parseInt(ac.getLimit()));
        }
    }

    public static class LocalCacheCondition extends CsitsCacheCondition {

        public LocalCacheCondition() {
            super("caffeine");
        }

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
            return ConditionOutcome.match();
        }
    }
}
