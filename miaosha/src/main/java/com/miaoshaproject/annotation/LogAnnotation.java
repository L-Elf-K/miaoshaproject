package com.miaoshaproject.annotation;

import java.lang.annotation.*;

/**
 * Created by kk on 2021/1/3
 */
@Target(ElementType.METHOD)//注解放置的目标位置
@Retention(RetentionPolicy.RUNTIME)//注解在那个阶段执行
@Documented
public @interface LogAnnotation {
    String modelName() default ""; //接口名称
    String modelFunc() default ""; //接口功能
    String modelLevel() default ""; //日志级别
}
