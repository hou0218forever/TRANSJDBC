package com.example.demo.logs;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author HouZhiBo
 * @version 创建时间：2018年10月30日 下午3:41:21
 * @ClassName 类名称
 * @Description 类描述
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLogs {

	/**
	 * 描述
	 */
	String description() default "";
}
