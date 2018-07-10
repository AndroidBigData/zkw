package com.zjwam.zkw.util;

import com.google.gson.Gson;
import com.zjwam.zkw.entity.SimpleResponse;

public class MyException extends IllegalStateException {

    private SimpleResponse errorBean;

    public MyException(String s) {
        super(s);
        errorBean = new Gson().fromJson(s, SimpleResponse.class);
    }

    public SimpleResponse getErrorBean() {
        return errorBean;
    }
}
