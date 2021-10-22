package org.csits.platform.autoconfigure.cache.properties;

import com.alicp.jetcache.anno.SerialPolicy;

public class RedisAreaCache extends AreaCache {

    private String keyPrefix;
    private String connectionFactory;
    private String valueEncoder = SerialPolicy.KRYO;
    private String valueDecoder = SerialPolicy.KRYO;

    public String getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(String connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public String getValueEncoder() {
        return valueEncoder;
    }

    public void setValueEncoder(String valueEncoder) {
        this.valueEncoder = valueEncoder;
    }

    public String getValueDecoder() {
        return valueDecoder;
    }

    public void setValueDecoder(String valueDecoder) {
        this.valueDecoder = valueDecoder;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
