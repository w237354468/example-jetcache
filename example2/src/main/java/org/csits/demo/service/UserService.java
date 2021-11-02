package org.csits.demo.service;

import com.alicp.jetcache.anno.Cached;
import org.csits.demo.domain.User;

import static com.alicp.jetcache.anno.CacheType.BOTH;
import static com.alicp.jetcache.anno.CacheType.LOCAL;

public interface UserService {

    @Cached(name = "UserService.getUserById", cacheType = LOCAL, expire = 3600)
    User getUserById(Integer id);
}