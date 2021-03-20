package com.jf.common.aspect.log;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * AOP日志处理类
 *
 * @author 江峰
 * @date 2020/5/18 1:41 下午
 */
@Aspect
@Component
public class LoggerHandler {
	/**
	 * 切点 --- 包含HttpLogger注解
	 */
	@Pointcut("@annotation(com.jf.common.aspect.log.MethodLogger)")
	protected void methodLogger() {
	}

	@Around("methodLogger()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

		long start = System.currentTimeMillis();

		Signature signature = joinPoint.getSignature();
		MethodSignature msg = (MethodSignature) signature;
		Object target = joinPoint.getTarget();
		// 获取注解标注的方法
		Method method = target.getClass().getMethod(msg.getName(),
				msg.getParameterTypes());

		Logger log = LoggerFactory
				.getLogger(joinPoint.getSignature().getDeclaringType());
		String methodName = joinPoint.getSignature().getName();
		// 通过方法获取注解
		MethodLogger methodLogger = method.getAnnotation(MethodLogger.class);
		// 是否打印入参数
		if (methodLogger.logType().equals(LogTypeEnum.FULL)
				|| methodLogger.logType().equals(LogTypeEnum.REQUEST_PARAM)) {
			Object[] args = joinPoint.getArgs();
			Stream<?> stream = CollectionUtils.isEmpty(Arrays.asList(args))
					? Stream.empty()
					: Arrays.stream(args);
			List<Object> logArgs = stream
					.filter(arg -> (!(arg instanceof HttpServletRequest)
							&& !(arg instanceof HttpServletResponse)))
					.collect(Collectors.toList());
			log.info("请求方法: {}, 请求参数: {}", methodName,
					JSONObject.toJSONString(logArgs));
		}

		Object result = joinPoint.proceed();
		long elapsedTime = System.currentTimeMillis() - start;
		if (methodLogger.logType().equals(LogTypeEnum.REQUEST_PARAM)) {
			// 不打印出参数，针对列表类型的不打印
			log.info("请求方法: {}, 耗时: {}", methodName, elapsedTime);
		} else {
			log.info("请求方法: {}, 返回结果: {}, 耗时: {}ms", methodName,
					JSONObject.toJSONString(result), elapsedTime);
		}
		return result;
	}
}
