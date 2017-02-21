package com.ly1054.birsker.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @项目名： MiFengJi
 * @包名： com.ly1054.birsker.annotation
 * @文件名: Lib_OnClick
 * @创建者: 刘勇
 * @创建时间: 2017/02/18  17:10
 * @描述： 注解
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Lib_OnClick {

    String[] value() default "";
}
