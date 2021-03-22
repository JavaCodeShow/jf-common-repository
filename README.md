# mvn-repo

## 一、微服务常用统一工具类

- 请求（返回）参数打印日志处理
- 全局异常处理
- 返回值（BaseResult）,含分页查询请求参数、分页查询返回结果
- 常用工具类（日期等）

## 二、使用方式

1. 引入依赖

```
<dependency>
    <groupId>com.jf</groupId>
    <artifactId>jf-common-utils</artifactId>
    <version>1.0</version>
</dependency>
```

2. 导入该模块的配置类，在系统的启动类上面添加这一行代码即可。

   ```
   @Import({ JfCommonUtilsConfig.class })
   ```

## 三、结束

每个服务通过引入该工具类，实现了对入参日志，出参日志，全局异常，接口返回值等的统一处理。