package org.csits.platform.autoconfigure.cache.configure;

import com.alicp.jetcache.CacheBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AutoConfigureBeans {

    public static Map<String, CacheBuilder> LOCAL_CACHE_BUILDERS = new HashMap<>();

    public static Map<String, CacheBuilder> REMOTE_CACHE_BUILDERS = new HashMap<>();

    public static Map<String, Object> CUSTOMER_CONTAINER = Collections.synchronizedMap(new HashMap<>());

}
