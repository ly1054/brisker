package com.ly1054.brisker;

/**
 * @项目名： MiFengJi
 * @包名： com.ly1054.brisker
 * @文件名: Briskerable
 * @创建者: 刘勇
 * @创建时间: 2017/02/17  11:20
 * @描述： TODO
 */


public interface Briskerable<T> {

    void inject(T host,Object object);

}
