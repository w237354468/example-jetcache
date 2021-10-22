package org.csits.demo;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMethodCache(basePackages = "org.csits.demo")
@EnableCreateCacheAnnotation
// 这两个注解分别激活Cached和CreateCache注解
public class CacheApplication2 {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication2.class);
    }
}
