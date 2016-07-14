package com.adanac.tool.rageon.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * url构建
 */
public class UrlBuilder {

    private StringBuilder builder = new StringBuilder();

    public static UrlBuilder create(){
        return  new UrlBuilder();
    }

    /**
     * 添加url参数前缀
     * @param prefix
     * @return
     */
    public UrlBuilder addPrefix(String prefix){
        builder.append(prefix);
        return this;
    }

    /**
     * 添加查询参数
     * @param key key
     * @param value value
     * @param hasPrefix 是否添加前缀“&”
     * @return
     */
    public UrlBuilder append(String key,String value,boolean hasPrefix){
        if (hasPrefix){
            builder.append("&");
        }

        builder.append(key);
        builder.append("=");
        try {
            builder.append(URLEncoder.encode(value,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw  new RuntimeException("not support UTF-8");
        }
        return this;
    }

    //添加前缀 &
    public UrlBuilder append(String key,String value){
        return append(key,value,true);
    }

    //不添加前缀 &
    public UrlBuilder appendWithoutPrefix(String key,String value){
        return append(key,value,false);
    }

    public String build(){
        return builder.toString();
    }
}
