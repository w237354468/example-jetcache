package org.csits.platform.autoconfigure.cache.convertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Function;

public class JacksonKeyConvertor implements Function<Object, Object> {

    public static final JacksonKeyConvertor INSTANCE = new JacksonKeyConvertor();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object apply(Object originalKey) {
        if (originalKey == null) {
            return null;
        }
        if (originalKey instanceof String) {
            return originalKey;
        }
        try {
            return mapper.writeValueAsString(originalKey);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
