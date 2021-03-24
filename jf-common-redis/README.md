# jf-common-redis

## 目的

> 实现对Redis,Redisson常用操作的统一封装处理。便于后续各个服务接入Redis，以及Redisson。进行缓存以及分布式锁的处理

## 引入流程

1. 在自己的服务里面引入依赖

```
<dependency>
    <groupId>com.jf</groupId>
    <artifactId>jf-common-redis</artifactId>
    <version>1.0</version>
</dependency>
```

2. 引入redis相关的配置

   ```
   # Redis相关配置
   spring:
     redis:
       host: 139.224.103.236
       port: 6379
       # 连接超时时间（毫秒）
       timeout: 10000
       # Redis默认情况下有16个分片，这里配置具体使用的分片，默认是0
       database: 0
       # 连接池最大连接数（使用负值表示没有限制） 默认 8
       lettuce.pool:
         max-active: 8
         # 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
         max-wait: -1
         # 连接池中的最大空闲连接 默认 8
         max-idle: 8
         # 连接池中的最小空闲连接 默认 0
         min-idle: 0
       #集群
       #cluster.nodes: IP:7001
   
     redisson:
       address: "redis://139.224.103.236:6379"
   ```

3. 导入该模块的配置类，在系统的启动类上面添加这一行代码即可。

   ```
   @Import({ JfCommonRedisConfig.class })
   ```

## 使用方式

1. 缓存相关：将我们工具类里面定义的RedisService注入，调用相应的方法操作即可。

   ```
   @Autowired
   private RedisService redisService;
   
   ```

2. 分布式锁相关：将我们工具类里面定义的RedissonLockService注入，调用相应的方法操作即可。

   ```
   @Autowired
   private RedissonLockService redissonLockService;
   ```


3. 表单防重复提交注解：@ReSubmitLock

   > 默认10秒钟内重复的提交拒绝执行。可自定义设置时间


4. 分布式锁注解：@DistributeLock(lockKey = "key的值")

> 必须要指定key的值，key值建议：系统code：类的名称：方法名称
>
> 锁等待时间：默认2s
>
> 锁自动释放时间：默认60s

## 结束

每个服务通过引入该工具类，实现了各个服务对Redis以及Redisson的统一处理。

好处：

> 避免各个服务定义自己的对Redis，Redisson的操作配置等等。
>
> 有利于对Redis相关代码的统一管理。
>
> 提高了代码的复用性，避免了代码的冗余。



