package org.csits.demo.service;

import com.alicp.jetcache.anno.Cached;

public interface UserService {

    @Cached(name = "UserService.getUserById", expire = 3600)
    void getUserById(long userId);
}