package com.ianeiu.utils.paramvalid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数校验注解
 * @author wuweimian
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamValid {

    boolean notNull() default false;

    String desc() default "";

    boolean validStrLength() default false;
    int maxLength() default 50;
    int minLength() default 0;

    boolean validNum() default false;
    int maxNum() default 9999;
    int minNum() default 0;


    String refField() default "";
    //当引用的字段为何值时，标记的字段必填
    String refValue() default "1";
}