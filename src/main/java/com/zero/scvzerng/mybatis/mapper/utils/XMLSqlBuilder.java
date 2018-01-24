package com.zero.scvzerng.mybatis.mapper.utils;


import java.util.function.Supplier;

/**
 *XML SQL生成帮助类
 * Created by scvzerng on 2017/5/9.
 */
public class XMLSqlBuilder {

    /**
     * foreach标签
     * @param collection 属性
     * @param item 属性
     * @param open 属性
     * @param close 属性
     * @param split 属性
     * @param body 循环体
     * @return
     */
    public static String foreach(String collection, String item, String open, String close, String split, Supplier<String> body){
        return String.format("<foreach collection='%s' item='%s' open='%s' close='%s' separator='%s' >%s</foreach>",collection,item,open,close,split,body.get()) ;
    }

    /**
     * foreach标签
     * @param collection 属性
     * @param item 属性
     * @param body 循环体
     * @return
     */
    public static String foreach(String collection, String item,Supplier<String> body){
       return  foreach(collection,item,"","","",body);
    }

    /**
     * trim标签
     * @param prefix 属性
     * @param prefixOverrides 属性
     * @param suffix 属性
     * @param body 内容
     * @return
     */
    public static String trim(String prefix,String prefixOverrides ,String suffix,String suffixOverrides,Supplier<String> body ){
        return String.format("<trim prefix='%s' prefixOverrides='%s' suffix='%s' suffixOverrides='%s'>%s</trim>",prefix,prefixOverrides,suffix,suffixOverrides,body.get());
    }

    /**
     * choose标签
     * @param when when条件
     * @param other other条件
     * @return
     */
    public static String choose(Supplier<String> when,Supplier<String> other){
        return String.format("<choose>%s<otherwise>%s</otherwise></choose>",when.get(),other.get());
    }

    /**
     * when标签
     * @param test 条件
     * @param body 内容
     * @return
     */
    public static String when(String test,Supplier<String> body){
        return String.format("<when test='%s'>%s</when>",test,body.get());
    }

    /**
     * bind标签
     * @param name 变量名
     * @param value 变量值
     * @return
     */
    public static String bind(String name,String value){
        return String.format("<bind name='%s' range='%s' />" ,name,value);
    }

    public static String equalsString(String key,String value){
        return String.format("%s==\"%s\"",key,value);
    }
    public static String notEquals(String key,String value){
        return String.format("%s!=%s",key,value);
    }
}
