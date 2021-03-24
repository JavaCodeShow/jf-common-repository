# jf-common-utils

## 微服务常用统一工具类

- 请求（返回）参数打印日志处理
- 全局异常处理
- 返回值（BaseResult）,含分页查询请求参数、分页查询返回结果
- 常用工具类（日期等）

## 引入流程

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

## 使用方式

### 日志

在controller层的接口方法上添加@MethodLogger即可。默认将请求参数和返回参数都打印出来

示例：

```
@GetMapping("/{id}")
@MethodLogger
public BaseResult<OrderDTO> getOrderById(@PathVariable Integer id) {
	return BaseResult.success(new OrderDTO());
}
```

### 返回值

> 成功返回：BaseResult.success(xxx)
>
> 失败返回：BaseResult.fail(xxx)

### 分页请求(返回)参数

> 请求参数：PageQueryRequest<T>
>
> 返回参数：PageQueryResponse.success(list, total)

示例代码：

```
@ApiOperation(value = "分页查询订单")
@PostMapping("/list")
@MethodLogger
public PageQueryResponse<List<OrderDTO>> orderList(
@RequestBody @Validated PageQueryRequest<OrderDTO> request) {

	OrderDTO orderDTO = request.getData();
	List<OrderDTO> orderList = orderService.findxxx();
  
	return PageQueryResponse.success(orderList, 1);
}

```

请求参数：

```
{
    "currPage": 1,
    "pageSize": 10,
    "data": {
        "id": 1
    }
}
```

## 结束

每个服务通过引入该工具类，实现了对入参日志，出参日志，全局异常，接口返回值等的统一处理。