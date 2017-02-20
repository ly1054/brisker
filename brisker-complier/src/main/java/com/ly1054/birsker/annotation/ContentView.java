package com.ly1054.birsker.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @项目名： MiFengJi
 * @包名： com.ly1054.birsker.annotation
 * @文件名: ContentView
 * @创建者: 刘勇
 * @创建时间: 2017/02/17  16:24
 * @描述： TODO
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface ContentView {

    int value() default 0;

}
