package com.miaoshaproject.annotation;

import java.lang.annotation.*;

/**
 * Created by kk on 2021/1/3
 */
@Target(ElementType.METHOD)//注解放置的目标位置
@Retention(RetentionPolicy.RUNTIME)//注解在那个阶段执行
@Documented
public @interface LogAnnotation {
    String operModel() default ""; //操作模块
    String operType() default ""; //操作类型
    String operDesc() default ""; //操作说明
}
