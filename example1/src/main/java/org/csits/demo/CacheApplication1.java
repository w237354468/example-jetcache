package org.csits.demo;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMethodCache(basePackages = "org.csits.demo")
@EnableCreateCacheAnnotation
public class CacheApplication1 {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication1.class);
    }
}
