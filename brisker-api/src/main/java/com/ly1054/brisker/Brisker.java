package com.ly1054.brisker;


import android.util.Log;
import android.view.View;

/**
 * @项目名： MiFengJi
 * @包名： com.ly1054.brisker
 * @文件名: Brisker
 * @创建者: 刘勇
 * @创建时间: 2017/02/17  11:43
 * @描述： brisker
 */

@SuppressWarnings("unchecked")
public class Brisker {

    //注解实现activity,fragment的依赖注入
    public static void inject(Object host){

        //找到apt 生成的注解类
        String className = host.getClass().getName();
        className += "$$Briskerable";
        Log.e("class name",className);
        Briskerable brisker = null;
        try {
            Class clazz = Class.forName(className);
            brisker = (Briskerable) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (brisker != null){
            brisker.inject(host,host);
        }
    }

    public static void inject(Object host, View view){
        //找到apt 生成的注解类
        String className = host.getClass().getName();
        className += "$$Briskerable";
        Log.e("class name",className);
        Briskerable brisker = null;
        try {
            Class clazz = Class.forName(className);
            brisker = (Briskerable) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (brisker != null){
            brisker.inject(host,view);
        }
    }

}
