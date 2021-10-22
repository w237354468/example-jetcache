package org.csits.platform.autoconfigure.cache.condition;

import org.csits.platform.component.cache.ConfigTree;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CsitsCacheCondition extends SpringBootCondition {

    public static final String CSITS_CACHE_PREFIX = "csits.cache.";
    private final String cacheType;

    protected CsitsCacheCondition(String cacheType) {
        Objects.requireNonNull(cacheType, "cacheType can't be null");
        this.cacheType = cacheType;
    }

    // 通用的remote注入
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext conditionContext,
                                            AnnotatedTypeMetadata annotatedTypeMetadata) {
        ConfigTree ct = new ConfigTree((ConfigurableEnvironment) conditionContext.getEnvironment(),
                CSITS_CACHE_PREFIX);
        if (matchRemote(ct)) {
            return ConditionOutcome.match();
        } else {
            return ConditionOutcome.noMatch("no match for " + cacheType);
        }
    }

    private boolean matchRemote(ConfigTree ct) {
        Map<String, Object> m = ct.subTree("remote.").getProperties();

        Set<String> cacheAreaNames = m.keySet().stream().map((s) -> s.substring(0, s.indexOf('.')))
                .collect(
                        Collectors.toSet());
        return cacheAreaNames.stream().anyMatch((s) -> cacheType.equals(m.get(s + ".type")));
    }
}
