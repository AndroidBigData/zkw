package com.zjwam.zkw.HttpUtils;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.MineClassBean;
import com.zjwam.zkw.entity.MineIntegralBean;
import com.zjwam.zkw.entity.MineShopCartBean;
import com.zjwam.zkw.entity.PersonalCollectionBean;
import com.zjwam.zkw.entity.PersonalNoteBookBean;
import com.zjwam.zkw.entity.PersonalOrderBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.SimpleResponse;
import com.zjwam.zkw.personalcenter.MineClassActivity;
import com.zjwam.zkw.personalcenter.MineCollectionActivity;
import com.zjwam.zkw.personalcenter.MineIntegralActivity;
import com.zjwam.zkw.personalcenter.MineNoteBookActivity;
import com.zjwam.zkw.personalcenter.MineOrderActivity;
import com.zjwam.zkw.personalcenter.MineShopCartActivity;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.Map;

public class PersonalCenterHttp {
    private Context context;
    private Map<String , String> param;

    public PersonalCenterHttp(Context context) {
        this.context = context;
    }
    /**
     * 我的课程
     */
    public void getLearnAll(String uid,String type,String page){
        param = new HashMap<>();
        param.put("uid",uid);
        param.put("type",type);
        param.put("page",page);
        OkGoUtils.postRequets(Config.URL + "api/user/all_class", context, param, new Json2Callback<MineClassBean>() {
            @Override
            public void onSuccess(Response<MineClassBean> response) {
                if (context instanceof MineClassActivity&&!((MineClassActivity) context).isFinishing()){
                    ((MineClassActivity) context).getLearnAll(response);
                }
            }

            @Override
            public void onError(Response<MineClassBean> response) {
                super.onError(response);
                if (context instanceof MineClassActivity&&!((MineClassActivity) context).isFinishing()){
                    ((MineClassActivity) context).getLearnAllError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineClassActivity&&!((MineClassActivity) context).isFinishing()){
                    ((MineClassActivity) context).getLearnAllFinish();
                }
            }
        });
    }
    public void getLearning(String uid,String type,String page){
        param = new HashMap<>();
        param.put("uid",uid);
        param.put("type",type);
        param.put("page",page);
        OkGoUtils.postRequets(Config.URL + "api/user/all_class", context, param, new Json2Callback<MineClassBean>() {
            @Override
            public void onSuccess(Response<MineClassBean> response) {
                if (context instanceof MineClassActivity&&!((MineClassActivity) context).isFinishing()){
                    ((MineClassActivity) context).getLearning(response);
                }
            }

            @Override
            public void onError(Response<MineClassBean> response) {
                super.onError(response);
                if (context instanceof MineClassActivity&&!((MineClassActivity) context).isFinishing()){
                    ((MineClassActivity) context).getLearningError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineClassActivity&&!((MineClassActivity) context).isFinishing()){
                    ((MineClassActivity) context).getLearningFinish();
                }
            }
        });
    }
    public void getLearnOver(String uid,String type,String page){
        param = new HashMap<>();
        param.put("uid",uid);
        param.put("type",type);
        param.put("page",page);
        OkGoUtils.postRequets(Config.URL + "api/user/all_class", context, param, new Json2Callback<MineClassBean>() {
            @Override
            public void onSuccess(Response<MineClassBean> response) {
                if (context instanceof MineClassActivity&&!((MineClassActivity) context).isFinishing()){
                    ((MineClassActivity) context).getLearnOver(response);
                }
            }

            @Override
            public void onError(Response<MineClassBean> response) {
                super.onError(response);
                if (context instanceof MineClassActivity&&!((MineClassActivity) context).isFinishing()){
                    ((MineClassActivity) context).getLearnOverError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineClassActivity&&!((MineClassActivity) context).isFinishing()){
                    ((MineClassActivity) context).getLearnOverFinish();
                }
            }
        });
    }

    /**
     * 我的笔记
     */
    public void getNoteBookData(String uid,String page){
        param = new HashMap<>();
        param.put("uid",uid);
        param.put("page",page);
        OkGoUtils.postRequets(Config.URL + "api/user/note", context, param, new JsonCallback<ResponseBean<PersonalNoteBookBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<PersonalNoteBookBean>> response) {
                if (context instanceof MineNoteBookActivity && !((MineNoteBookActivity) context).isFinishing()){
                    ((MineNoteBookActivity) context).getNoteBookData(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<PersonalNoteBookBean>> response) {
                super.onError(response);
                if (context instanceof MineNoteBookActivity && !((MineNoteBookActivity) context).isFinishing()){
                    ((MineNoteBookActivity) context).getNoteBookDataError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineNoteBookActivity && !((MineNoteBookActivity) context).isFinishing()){
                    ((MineNoteBookActivity) context).getNoteBookDataFinish();
                }
            }
        });
    }

    public void deleNoteBook(String uid,String id){
        param = new HashMap<>();
        param.put("uid",uid);
        param.put("id",id);
        OkGoUtils.postRequets(Config.URL + "api/user/note_del", context, param, new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (context instanceof MineNoteBookActivity && !((MineNoteBookActivity) context).isFinishing()){
                    ((MineNoteBookActivity) context).deleNoteBook(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                super.onError(response);
                if (context instanceof MineNoteBookActivity && !((MineNoteBookActivity) context).isFinishing()){
                    ((MineNoteBookActivity) context).deleNoteBookError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineNoteBookActivity && !((MineNoteBookActivity) context).isFinishing()){
                    ((MineNoteBookActivity) context).deleNoteBookFinish();
                }
            }
        });
    }
    /**
     * 我的收藏
     */
    public void getCollection(String uid,String page){
        param = new HashMap<>();
        param.put("uid",uid);
        param.put("page",page);
        OkGoUtils.postRequets(Config.URL + "api/user/hold", context, param, new JsonCallback<ResponseBean<PersonalCollectionBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<PersonalCollectionBean>> response) {
                if (context instanceof MineCollectionActivity && !((MineCollectionActivity) context).isFinishing()){
                    ((MineCollectionActivity) context).getCollectio(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<PersonalCollectionBean>> response) {
                super.onError(response);
                if (context instanceof MineCollectionActivity && !((MineCollectionActivity) context).isFinishing()){
                    ((MineCollectionActivity) context).getCollectioError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineCollectionActivity && !((MineCollectionActivity) context).isFinishing()){
                    ((MineCollectionActivity) context).getCollectioFinish();
                }
            }
        });
    }
    public void offCollection(String uid,String id){
        param = new HashMap<>();
        param.put("uid",uid);
        param.put("id",id);
        OkGoUtils.postRequets(Config.URL + "api/user/run_hold", context, param, new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (context instanceof MineCollectionActivity && !((MineCollectionActivity) context).isFinishing()){
                    ((MineCollectionActivity) context).offCollection(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                super.onError(response);
                if (context instanceof MineCollectionActivity && !((MineCollectionActivity) context).isFinishing()){
                    ((MineCollectionActivity) context).offCollectionError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineCollectionActivity && !((MineCollectionActivity) context).isFinishing()){
                    ((MineCollectionActivity) context).offCollectionFinish();
                }
            }
        });
    }
    /**
     * 我的积分
     */
    public void getMineIntegral(String uid,String type,String page){
        param = new HashMap<>();
        param.put("uid",uid);
        param.put("type",type);
        param.put("page",page);
        OkGoUtils.postRequets(Config.URL + "api/user/jifen", context, param, new JsonCallback<ResponseBean<MineIntegralBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<MineIntegralBean>> response) {
                if (context instanceof MineIntegralActivity && !((MineIntegralActivity) context).isFinishing()){
                    ((MineIntegralActivity) context).getMineIntegral(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<MineIntegralBean>> response) {
                super.onError(response);
                if (context instanceof MineIntegralActivity && !((MineIntegralActivity) context).isFinishing()){
                    ((MineIntegralActivity) context).getMineIntegralError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineIntegralActivity && !((MineIntegralActivity) context).isFinishing()){
                    ((MineIntegralActivity) context).getMineIntegralFinish();
                }
            }
        });
    }
    /**
     * 我的订单
     */
    public void getMineOrder(String uid,String type,String page){
        param = new HashMap<>();
        param.put("uid",uid);
        param.put("type",type);
        param.put("page",page);
        OkGoUtils.postRequets(Config.URL + "api/user/order", context, param, new JsonCallback<ResponseBean<PersonalOrderBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<PersonalOrderBean>> response) {
                if (context instanceof MineOrderActivity && !((MineOrderActivity) context).isFinishing()){
                    ((MineOrderActivity) context).getMineOrder(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<PersonalOrderBean>> response) {
                super.onError(response);
                if (context instanceof MineOrderActivity && !((MineOrderActivity) context).isFinishing()){
                    ((MineOrderActivity) context).getMineOrderError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MineOrderActivity && !((MineOrderActivity) context).isFinishing()){
                    ((MineOrderActivity) context).getMineOrderFinish();
                }
            }
        });
    }
    /**
     * 购物车
     */
    public void getMineShopCard(String uid){
        param = new HashMap<>();
        param.put("uid",uid);
        OkGoUtils.postRequets(Config.URL + "api/user/car", context, param, new JsonCallback<ResponseBean<MineShopCartBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<MineShopCartBean>> response) {
                if (context instanceof MineShopCartActivity&&!((MineShopCartActivity) context).isFinishing()){
                    ((MineShopCartActivity) context).getMineShopCard(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<MineShopCartBean>> response) {
                super.onError(response);
                if (context instanceof MineShopCartActivity&&!((MineShopCartActivity) context).isFinishing()){
                    ((MineShopCartActivity) context).getMineShopCardError(response);
                }
            }
        });
    }
    public void deleteShopCard(String uid,String id){
        param = new HashMap<>();
        param.put("uid",uid);
        param.put("id",id);
        OkGoUtils.postRequets(Config.URL + "api/user/car_del", context, param, new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (context instanceof MineShopCartActivity && !((MineShopCartActivity) context).isFinishing()){
                    ((MineShopCartActivity) context).deleteShopCard(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                super.onError(response);
                if (context instanceof MineShopCartActivity && !((MineShopCartActivity) context).isFinishing()){
                    ((MineShopCartActivity) context).deleteShopCardError(response);
                }
            }
        });
    }
}
