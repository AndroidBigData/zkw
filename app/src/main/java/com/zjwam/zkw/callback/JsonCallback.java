/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zjwam.zkw.callback;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.SimpleResponse;
import com.zjwam.zkw.util.MyException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2016/1/14
 * 描    述：默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * 修订历史：
 * ================================================
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的是模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) {

        //详细自定义的原理和文档，看这里： https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback

        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        Type type = params[0];
        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型!");
        Type rawType = ((ParameterizedType) type).getRawType();
        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        ResponseBody body = response.body();
        if (body == null) return null;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());
        if (rawType != ResponseBean.class){
            T data = gson.fromJson(jsonReader,type);
            response.close();
            return  data;
        }else {
            if (typeArgument == Void.class){
                SimpleResponse simpleResponse = gson.fromJson(jsonReader,SimpleResponse.class);
                response.close();
                return (T) simpleResponse.toLzyResponse();
            }else {
                ResponseBean responseBean = gson.fromJson(jsonReader,type);
                response.close();
                int code = responseBean.code;
                //与服务器约定的各种code
                if (code == 1){
                    return (T) responseBean;
                } else {
                    throw new MyException(responseBean.toString());
                }
            }
        }
    }

//    @Override
//    public void onError(com.lzy.okgo.model.Response<T> response) {
//        Throwable exception = response.getException();
//        if (exception != null) exception.printStackTrace();
//        if (exception instanceof UnknownHostException || exception instanceof ConnectException){
//            System.out.println("网络连接失败，请连接网络");
//        }else if (exception instanceof SocketTimeoutException){
//            System.out.println("网络请求超时");
//        }else if (exception instanceof HttpException){
//            System.out.println("服务端响应码404和500");
//        }else if (exception instanceof StorageException){
//            System.out.println("SD卡不存在或者没有权限");
//        }
//    }
}
