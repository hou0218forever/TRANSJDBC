package com.example.demo.logs;

import java.lang.annotation.*;

/**
 * @author keven
 * @date 2018-08-12 下午11:44
 * @Description  自定义操作日志标签，模块名和方法名
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptionalLog {

    /**
     * 操作模块
     */
    String module()  default "";

    /**
     * 操作方法
     */
    String methods()  default "";

    /**
     * 类型
     */
    String type() default "";

}
