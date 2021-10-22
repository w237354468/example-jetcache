package org.csits.demo.service;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @CreateCache(name = "CacheService.strCache", expire = 20, cacheType = CacheType.LOCAL, localLimit = 5)
    public Cache<Long, String> strCache;

}
