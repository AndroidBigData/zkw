package com.zjwam.zkw.httputils;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.PayMsgBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.WxPayBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.webview.WebViewActivity;

import java.util.HashMap;
import java.util.Map;

public class WebViewHttp {
    private Context context;
    private Map<String,String> map;

    public WebViewHttp(Context context) {
        this.context = context;
    }
    public void getWxPay(String uid,String type){
        map = new HashMap<>();
        map.put("uid",uid);
        map.put("type",type);
        OkGoUtils.postRequets(Config.URL + "api/pay/pay_card", context, map, new JsonCallback<ResponseBean<WxPayBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<WxPayBean>> response) {
                if (context instanceof WebViewActivity){
                    ((WebViewActivity) context).getWxPay(response);
                }
            }
            @Override
            public void onError(Response<ResponseBean<WxPayBean>> response) {
                super.onError(response);
                if (context instanceof WebViewActivity){
                    ((WebViewActivity) context).getWxPayError(response);
                }
            }
        });
    }
    public void getAliPay(String uid,String type){
        map = new HashMap<>();
        map.put("uid",uid);
        map.put("type",type);
        OkGoUtils.postRequets(Config.URL + "api/pay/pay_card", context, map, new JsonCallback<ResponseBean<PayMsgBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<PayMsgBean>> response) {
                if (context instanceof WebViewActivity){
                    ((WebViewActivity) context).getAliPay(response);
                }
            }
            @Override
            public void onError(Response<ResponseBean<PayMsgBean>> response) {
                super.onError(response);
                if (context instanceof WebViewActivity){
                    ((WebViewActivity) context).getAliPayError(response);
                }
            }
        });
    }
}
