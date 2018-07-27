package com.zjwam.zkw.HttpUtils;

import android.util.Log;

import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.zjwam.zkw.util.MyException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class HttpErrorMsg {
    public static String getErrorMsg(Throwable exception){
        if (exception != null) exception.printStackTrace();
        if (exception instanceof UnknownHostException || exception instanceof ConnectException){
            System.out.println("网络连接失败，请连接网络");
            return "网络连接失败，请连接网络";
        }else if (exception instanceof SocketTimeoutException){
            System.out.println("网络请求超时");
            return "网络请求超时";
        }else if (exception instanceof HttpException){
            System.out.println("服务端响应码404和500");
            return "服务端响应码404和500";
        }else if (exception instanceof StorageException){
            System.out.println("SD卡不存在或者没有权限");
            return "SD卡不存在或者没有权限";
        }else if (exception instanceof MyException){
            return ((MyException) exception).getErrorBean().msg;
        }else {
            return exception.toString();
        }
    }
}
