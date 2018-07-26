package com.zjwam.zkw.HttpUtils;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.PayMsgBean;
import com.zjwam.zkw.entity.PayPreviewBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.WxPayBean;
import com.zjwam.zkw.pay.PayPreviewActivity;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class PayPreviewHttp {
    private Context context;
    private Map<String,String> param;

    public PayPreviewHttp(Context context) {
        this.context = context;
    }

    public void getPreviewMsg(String uid, String idtype, String id) {
        param = new HashMap<>();
        param.put("uid",uid);
        param.put(idtype,id);
        OkGoUtils.postRequets(Config.URL + "api/pay/pay_verify", context, param, new JsonCallback<ResponseBean<PayPreviewBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<PayPreviewBean>> response) {
                if (context instanceof PayPreviewActivity && !((PayPreviewActivity) context).isFinishing()){
                    ((PayPreviewActivity) context).getPreviewMsg(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<PayPreviewBean>> response) {
                super.onError(response);
                if (context instanceof PayPreviewActivity && !((PayPreviewActivity) context).isFinishing()){
                    ((PayPreviewActivity) context).getPreviewMsgError(response);
                }
            }
        });
    }
    public void aliPay(String uid,String id,String type){
        param = new HashMap<>();
        param.put("uid",uid);
        param.put("id",id);
        param.put("type",type);
        OkGoUtils.postRequets(Config.URL + "api/pay/pay", context, param, new JsonCallback<ResponseBean<PayMsgBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<PayMsgBean>> response) {
                if (context instanceof PayPreviewActivity && !((PayPreviewActivity) context).isFinishing()){
                    ((PayPreviewActivity) context).aliPay(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<PayMsgBean>> response) {
                super.onError(response);
                if (context instanceof PayPreviewActivity && !((PayPreviewActivity) context).isFinishing()){
                    ((PayPreviewActivity) context).aliPayError(response);
                }
            }
        });
    }
    public void wxPay(String uid,String id,String type){
        param = new HashMap<>();
        param.put("uid",uid);
        param.put("id",id);
        param.put("type",type);
        OkGoUtils.postRequets(Config.URL + "api/pay/pay", context, param, new JsonCallback<ResponseBean<WxPayBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<WxPayBean>> response) {
                if (context instanceof PayPreviewActivity && !((PayPreviewActivity) context).isFinishing()){
                    ((PayPreviewActivity) context).wxPay(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<WxPayBean>> response) {
                super.onError(response);
                if (context instanceof PayPreviewActivity && !((PayPreviewActivity) context).isFinishing()){
                    ((PayPreviewActivity) context).wxPayError(response);
                }
            }
        });
    }
}
