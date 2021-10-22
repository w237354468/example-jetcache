package org.csits.demo.controller;

import org.csits.demo.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    @Autowired
    CacheService cacheService;

    @GetMapping("get")
    public String get(@RequestParam("key") Long key) {
        return cacheService.strCache.get(key);
    }

    @GetMapping("put")
    public String get(@RequestParam("key") Long key,
                      @RequestParam("value") String value) {
        cacheService.strCache.put(key, value);
        return "success";
    }
}
