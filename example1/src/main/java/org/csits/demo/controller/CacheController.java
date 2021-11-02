package org.csits.demo.controller;

import org.csits.demo.service.impl.CacheServiceImpl;
import org.csits.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    @Autowired
    CacheServiceImpl cacheServiceImpl;

    @Autowired
    UserService userService;

    @GetMapping("getCache")
    public String get(@RequestParam("key") Long key) {
        return cacheServiceImpl.strCache.get(key);
    }

    @GetMapping("putCache")
    public String get(@RequestParam("key") Long key,
                      @RequestParam("value") String value) {
        cacheServiceImpl.strCache.put(key, value);
        return "success";
    }

    @GetMapping("selectUser")
    public String get(Integer id) {
        return userService.getUserById(id).toString();
    }
}
