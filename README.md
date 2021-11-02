## 缓存组件csits-cache-start的使用

> 该组件基于jetcache扩展     
> 支持本地与本地+远程方式使用   
> 支持方法注解与编程模式

## 添加Maven依赖

```xml
<dependency>
     <groupId>org.csits.platform</groupId>
     <artifactId>csits-cache-starter</artifactId>
</dependency>
```

## 添加配置

### 1.本地模式配置（目前只支持Caffeine）

> **当引入csits-cache-starter依赖后，无需进行任何配置，会自动以默认配置装配default域的本地Caffeine缓存**

```properties
# 本地缓存的可选配置项目

# 以毫秒为单位指定超时时间的全局配置
csits.cache.local.default.expireAfterWriteInMillis=100000000
# 以毫秒为单位，指定多长时间没有访问，就让缓存失效 0表示不使用这个功能
csits.cache.local.default.expireAfterAccessInMillis=0
csits.cache.local.default.limit=100
#本项默认注入为jackson，可进行自定义，详细见：5.1
csits.cache.local.default.keyConvertor=fastjson
```

> 当需要配置多个本地缓存时，使用域名称 ${area} 区分  
> 同时必须指定type配置项为caffeine才能开启新的域缓存

```properties
# 创建非default域的模板------需指定${area}与type属性=caffeine，${area}为域名称
csits.cache.local.${area}.type=caffeine

# 开启test域的缓存，并指定配置
csits.cache.local.test.type=caffeine
csits.cache.local.test.keyConvertor=none
csits.cache.local.test.expireAfterWriteInMillis=100000000
csits.cache.local.test.expireAfterAccessInMillis=0
csits.cache.local.test.limit=100

# 开启dev域的缓存，并初始化默认配置
csits.cache.local.dev.type=caffeine
```

### 2.两级缓存模式设置（目前仅支持Caffeine+Redis）

> **引入redis依赖后，输入配置项开启远程缓存**

```xml
<dependency>
  <groupId>org.csits.platform</groupId>
  <artifactId>csits-cache-starter</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

* 最小配置

```properties
csits.cache.remote.default.type=redis
spring.redis.host=localhost
spring.redis.port=6379
```

* 可选配置

```properties
csits.cache.remote.default.expireAfterWriteInMillis=100000000
csits.cache.remote.default.expireAfterAccessInMillis=0
csits.cache.remote.default.limit=100
#本项默认为jackson，可进行自定义，详细见：4.1
csits.cache.remote.default.keyConvertor=fastjson
#本项默认为KRYO序列化，可进行自定义
csits.cache.remote.default.valueEncoder=kryo
#本项默认为KRYO序列化，可进行自定义
csits.cache.remote.default.valueDecoder=kryo
csits.cache.remote.default.keyPrefix=100
csits.cache.remote.default.connectionFactory=redisConnectionFactory
```

* 多域配置（配置三个域）

```properties
csits.cache.remote.default.type=redis
csits.cache.remote.test.type=redis
csits.cache.remote.dev.type=redis
```

### 3.全局通用配置

```properties
# 统计缓存信息的间隔
csits.cache.statIntervalMinutes=15
# 是否把cacheName作为远程缓存key前缀，默认为false
csits.cache.areaInCacheName=false
# @Cached和@CreateCache自动生成name的时候，为了不让name太长，hiddenPackages指定的包名前缀被截掉
csits.cache.hidePackages=org.csits.platform
```

### 编写缓存代码

+ 在Spring 项目中开启缓存功能

```java
// 在Application类上添加注解
@SpringBootApplication
@EnableMethodCache(basePackages = "org.csits.demo")
@EnableCreateCacheAnnotation
public class CacheDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(CacheDemoApplication.class);
  }
}

```

+ 方法上的缓存（可添加在接口方法或者类方法上，需要保证为Spring Bean)

```java
public interface UserService {
    @Cached(name="UserService.getUserById", expire = 3600)
    User getUserById(long userId);
}
```

+ 编程模式， 创建缓存实例

```java
// 默认使用default域的缓存
@CreateCache(expire = 100)
private Cache<Long, UserDO> userCache;

```

CreateCache注解属性表

| 属性         | 默认值           | 说明                                                         |
| ------------ | ---------------- | ------------------------------------------------------------ |
| area         | “default”        | 如果需要连接多个缓存系统，可在配置多个cache area，这个属性指定要使用的那个area的name |
| name         | 未定义           | 指定缓存的名称，不是必须的，如果没有指定，会使用类名+方法名。name会被用于远程缓存的key前缀。另外在统计中，一个简短有意义的名字会提高可读性。如果两个`@CreateCache`的`name`和`area`相同，它们会指向同一个`Cache`实例 |
| expire       | 未定义           | 该Cache实例的默认超时时间定义，注解上没有定义的时候会使用全局配置，如果此时全局配置也没有定义，则取无穷大 |
| timeUnit     | TimeUnit.SECONDS | 指定expire的单位                                             |
| cacheType    | CacheType.REMOTE | 缓存的类型，包括CacheType.REMOTE、CacheType.LOCAL、CacheType.BOTH。如果定义为BOTH，会使用LOCAL和REMOTE组合成两级缓存 |
| localExpire    | 未定义 |  当cacheType=CacheType.BOTH时，本地缓存的失效时间。未配置则使用"expire"属性值 |
| localLimit   | 100           | 如果cacheType为CacheType.LOCAL或CacheType.BOTH，这个参数指定本地缓存的最大元素数量，以控制内存占用。注解上没有定义的时候会使用全局配置，如果此时全局配置也没有定义，则取100 |
| serialPolicy | KRYO           | 如果cacheType为CacheType.REMOTE或CacheType.BOTH，指定远程缓存的序列化方式。内置的可选值为SerialPolicy.JAVA和SerialPolicy.KRYO。 |
| keyConvertor | jacksonKeyConvertor           | 指定KEY的转换方式，用于将复杂的KEY类型转换为缓存实现可以接受的类型，可选值为jacksonKeyConvertor（自己实现）， FASTJSON，NONE。NONE表示不转换，FASTJSON通过fastjson将复杂对象KEY转换成String。 |

### 4. API介绍

* 小写API

```java
// 常用操作
V get(K key)
void put(K key, V value);
boolean putIfAbsent(K key, V value); //多级缓存MultiLevelCache不支持此方法
boolean remove(K key);
<T> T unwrap(Class<T> clazz);
Map<K,V> getAll(Set<? extends K> keys);
void putAll(Map<? extends K,? extends V> map);
void removeAll(Set<? extends K> keys);

// 当key对应的缓存不存在时，使用loader加载。通过这种方式，loader的加载时间可以被统计到。
V computeIfAbsent(K key, Function<K, V> loader)

// acheNullWhenLoaderReturnNull参数指定了当loader加载出来时null值的时候，是否要进行缓存
V computeIfAbsent(K key, Function<K, V> loader, boolean cacheNullWhenLoaderReturnNull)
    
// expire和timeUnit指定了缓存的超时时间，会覆盖缓存的默认超时时间。
V computeIfAbsent(K key, Function<K, V> loader, boolean cacheNullWhenLoaderReturnNull, long expire, TimeUnit timeUnit)
    
// put操作，expire和timeUnit指定了缓存的超时时间，会覆盖缓存的默认超时时间。
void put(K key, V value, long expire, TimeUnit timeUnit)

// 非堵塞的尝试获取一个锁，如果对应的key还没有锁，返回一个AutoReleaseLock，否则立即返回空。如果Cache实例是本地的，它是一个本地锁，在本JVM中有效；如果是redis等远程缓存，它是一个不十分严格的分布式锁。锁的超时时间由expire和timeUnit指定。多级缓存的情况会使用最后一级做tryLock操作
AutoReleaseLock tryLock(K key, long expire, TimeUnit timeUnit)
boolean tryLockAndRun(K key, long expire, TimeUnit timeUnit, Runnable action)
    
    // 用法如下
    // 使用try-with-resource方式，可以自动释放锁
    try(AutoReleaseLock lock = cache.tryLock("MyKey",100, TimeUnit.SECONDS)){
        if(lock != null){
        // do something
        }
    }

    // 上面的代码有个潜在的坑是忘记判断if(lock!=null)，所以一般可以直接用tryLockAndRun更加简单
    boolean hasRun = cache.tryLockAndRun("MyKey",100, TimeUnit.SECONDS, () -> {
        // do something
    });
```

* 大写API

> V get(K key)这样的方法虽然用起来方便，但有功能上的缺陷，当get返回null的时候，无法断定是对应的key不存在，还是访问缓存发生了异常，所以JetCache针对部分操作提供了另外一套API，提供了完整的返回值

```java
CacheGetResult<V> GET(K key);
MultiGetResult<K, V> GET_ALL(Set<? extends K> keys);
CacheResult PUT(K key, V value);
CacheResult PUT(K key, V value, long expireAfterWrite, TimeUnit timeUnit);
CacheResult PUT_ALL(Map<? extends K, ? extends V> map);
CacheResult PUT_ALL(Map<? extends K, ? extends V> map, long expireAfterWrite, TimeUnit timeUnit);
CacheResult REMOVE(K key);
CacheResult REMOVE_ALL(Set<? extends K> keys);
CacheResult PUT_IF_ABSENT(K key, V value, long expireAfterWrite, TimeUnit timeUnit);
```

> 这些方法的特征是方法名为大写，与小写的普通方法对应，提供了完整的返回值，用起来也稍微繁琐一些。例如：

```java
CacheGetResult<OrderDO> r = cache.GET(orderId);
if( r.isSuccess() ){
    OrderDO order = r.getValue();
} else if (r.getResultCode() == CacheResultCode.NOT_EXISTS) {
    System.out.println("cache miss:" + orderId);
} else if(r.getResultCode() == CacheResultCode.EXPIRED) {
    System.out.println("cache expired:" + orderId));
} else {
    System.out.println("cache get error:" + orderId);
}
```

### 5. 其他

#### 5.1 自定义KeyConvertor

```java
// 步骤一：定义KeyConvertor
public class JacksonKeyConvertor implements Function<Object, Object> {

    public static final JacksonKeyConvertor INSTANCE = new JacksonKeyConvertor();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object apply(Object originalKey) {
        if (originalKey == null) {
            return null;
        }
        if (originalKey instanceof String) {
            return originalKey;
        }
        try {
            return mapper.writeValueAsString(originalKey);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}

// 步骤二： 注入到容器中
@Bean("jacksonKeyConvertor")
public Function<Object, Object> customerKeyConvertor() {
  return JacksonKeyConvertor.INSTANCE;
}

// 步骤三： 
// csits.cache.local.default.keyConvertor=bean:jacksonKeyConvertor
```

### 6. Q&A

Q: 当有多个地方指定了缓存超时时间时，以那个为准？  
A:

1. put等方法上指定了超时时间，则以此时间为准
2. put等方法上未指定超时时间，使用Cache实例的默认超时时间
3. Cache实例的默认超时时间，通过在@CreateCache和@Cached上的expire属性指定，如果没有指定，使用yml中定义的全局配置，例如@Cached(
   cacheType=local)使用jetcache.local.default.expireAfterWriteInMillis，如果仍未指定则是无穷大