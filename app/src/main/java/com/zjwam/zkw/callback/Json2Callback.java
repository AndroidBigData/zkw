package com.zjwam.zkw.callback;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class Json2Callback<T> extends AbsCallback< T > {
    @Override
    public T convertResponse(Response response) {
        ResponseBody body = response.body();
        if (body == null) return null;
        T data = null;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());
        Type genType = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType)genType).getActualTypeArguments()[0];
        data = gson.fromJson(jsonReader,type);
        return data;
    }
}