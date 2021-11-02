package org.csits.demo.controller;

import org.csits.demo.service.CacheService;
import org.csits.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    @Autowired
    CacheService cacheService;

    @Autowired
    UserService userService;

    @GetMapping("get")
    public String get(@RequestParam("key") Long key) {
        return cacheService.strCache.get(key);
    }

    // 先put，后get
    @GetMapping("put")
    public String get(@RequestParam("key") Long key,
        @RequestParam("value") String value) {
        cacheService.strCache.put(key, value);
        return "success";
    }

    // 多次获取相同的User
    @GetMapping("funcget")
    public String getUser() {
        return userService.getUserById(1).toString();
    }
}
