package com.example.demo.aspect;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.OperateLog;
import com.example.demo.logs.OptionalLog;
import com.example.demo.util.IpUtils;

import org.apache.ibatis.javassist.ClassClassPath;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
public class OperateLogAspect {

	@Pointcut("@annotation(com.example.demo.logs.OptionalLog)")
	public void setLog() {

	}

	@Around("setLog()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		// 日志实体对象
		OperateLog log = new OperateLog();
		Object object = null;
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

		if (null == requestAttributes) {
			object = pjp.proceed();
			return object;
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		try {

			log.setIp(IpUtils.getRequestIp(request));

			// 拦截的实体类，就是当前正在执行的controller
			Object target = pjp.getTarget();
			// 拦截的方法名称。当前正在执行的方法
			String methodName = pjp.getSignature().getName();
			// 拦截的方法参数
			Object[] args = pjp.getArgs();

			String classType = pjp.getTarget().getClass().getName();
			Class<?> clazz = Class.forName(classType);
			String clazzName = clazz.getName();
			// 获取方法名称
			// 获取参数名称和值
			Map<String, Object> nameAndArgs = this.getFieldsName(this.getClass(), clazzName, methodName, args);
			// 获取请求路径
			String actionUrl = request.getRequestURI();

			// 拦截的放参数类型
			Signature sig = pjp.getSignature();
			MethodSignature msig;
			if (!(sig instanceof MethodSignature)) {
				throw new IllegalArgumentException("该注解只能用于方法");
			}
			msig = (MethodSignature) sig;
			Class<?>[] parameterTypes = msig.getMethod().getParameterTypes();

			// 获得被拦截的方法
			Method method;
			method = target.getClass().getMethod(methodName, parameterTypes);

			if (null != method) {
				// 获取方法（此为自定义注解）
				OptionalLog op = method.getAnnotation(OptionalLog.class);
				if (op == null) {
					object = pjp.proceed();
					return object;
				}
				// 获取注解的modules 设为操作模块
				log.setModule(op.module());
				// 获取注解的methods 设为执行方法
				log.setMethods(op.methods());
				log.setType(Integer.valueOf(op.type()));
				// 将上面获取到的请求路径 设为请求路径
				log.setUrl(actionUrl);
				try {
					log.setResult(1);
					log.setContent(JSONObject.toJSONString(nameAndArgs));
//					logService.create(log);
					object = pjp.proceed();
					return object;
				} catch (Throwable e) {
					log.setContent(JSONObject.toJSONString(nameAndArgs));
					log.setResult(2);
//					logService.create(log);
					object = pjp.proceed();
					return object;
				}
			}

		} catch (Throwable e) {
			return null;
		}
		return object;
	}

	/**
	 * 获取字段名和字段值
	 */
	private Map<String, Object> getFieldsName(Class<?> cls, String clazzName, String methodName, Object[] args)
			throws Exception {
		Map<String, Object> map = new HashMap<>();

		ClassPool pool = ClassPool.getDefault();
		ClassClassPath classPath = new ClassClassPath(cls);
		pool.insertClassPath(classPath);

		CtClass cc = pool.get(clazzName);
		CtMethod cm = cc.getDeclaredMethod(methodName);
		MethodInfo methodInfo = cm.getMethodInfo();
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
		if (attr == null) {
			// exception
		}
		int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
		System.out.println(cm.getParameterTypes().length);
		for (int i = 0; i < cm.getParameterTypes().length; i++) {
			map.put(attr.variableName(i + pos), args[i]);
		}
		return map;
	}
}
