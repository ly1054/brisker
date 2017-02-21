package com.ly1054.birsker.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @项目名： MiFengJi
 * @包名： com.ly1054.birsker.annotation
 * @文件名: OnClick
 * @创建者: 刘勇
 * @创建时间: 2017/02/18  17:09
 * @描述： TODO
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface OnClick {

    int[] value() default 0;
}
