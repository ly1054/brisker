package com.ly1054.birsker.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @项目名： MiFengJi
 * @包名： com.ly1054.brisker.annotation
 * @文件名: Bind
 * @创建者: 刘勇
 * @创建时间: 2017/02/16  16:10
 * @描述： TODO
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Bind {
    int value() default 0;
}
