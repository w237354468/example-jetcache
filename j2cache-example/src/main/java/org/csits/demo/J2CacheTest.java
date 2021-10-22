package org.csits.demo;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;
import net.oschina.j2cache.J2CacheBuilder;
import net.oschina.j2cache.J2CacheConfig;

public class J2CacheTest {

    public static void main(String[] args) {

        CacheChannel cache = J2Cache.getChannel();

        //缓存操作
        cache.set("default", "1", "Hello J2Cache");
        System.out.println(cache.get("default", "1"));
        cache.evict("default", "1");
        System.out.println(cache.get("default", "1"));

        cache.close();
    }

    public void dos() {
        J2CacheConfig config = new J2CacheConfig();
//填充 config 变量所需的配置信息
        J2CacheBuilder builder = J2CacheBuilder.init(config);
        CacheChannel channel = builder.getChannel();
//进行缓存的操作
        channel.close();
    }
}
