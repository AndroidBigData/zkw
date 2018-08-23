package com.zjwam.zkw.httputils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.callback.JsonCallback;

import org.json.JSONObject;

import java.util.Map;

public class OkGoUtils {
    /**
     * 不带缓存的网络请求
     * @param url
     * @param tag
     * @param callback
     * @param <T>
     */

    /**
     *固定数据格式：
     * code
     * msg
     * daga
     */
    public static <T>void getRequets(String url, Object tag, JsonCallback<T> callback){
        OkGo.<T>get(url)
                .tag(tag)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(callback);
    }
    public static <T>void postRequets(String url, Object tag, Map<String,String> params, JsonCallback<T> callback){
        OkGo.<T>post(url)
                .tag(tag)
                .params(params)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(callback);
    }
    /**
     *非固定数据格式
     */
    public static <T>void getRequets(String url, Object tag, Json2Callback<T> callback){
        OkGo.<T>get(url)
                .tag(tag)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(callback);
    }
    public static <T>void postRequets(String url, Object tag, Map<String,String> params, Json2Callback<T> callback){
        OkGo.<T>post(url)
                .tag(tag)
                .params(params)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(callback);
    }
    /**
     * JSON格式
     */
    public static <T>void postJSON(String url, Object tag, Map<String,String> params, JSONObject json, JsonCallback<T> callback){
        OkGo.<T>post(url)
                .tag(tag)
                .params(params)
                .upJson(json)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(callback);
    }
}
