package com.module.interceptor.annotation;

import java.lang.annotation.*;

/**
 * @author 何健锋
 * @version 1.0
 * @date 2018/3/8 11:41
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessRight {
//    @AliasFor("rightsCode")
    String rightsCode() default "-1";

}
