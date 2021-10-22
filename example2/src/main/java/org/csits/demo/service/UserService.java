package org.csits.demo.service;

import com.alicp.jetcache.anno.Cached;
import org.csits.demo.domain.User;

public interface UserService {
    @Cached(name = "UserService.getUserById", expire = 3600)
    User getUserById(long userId);
}