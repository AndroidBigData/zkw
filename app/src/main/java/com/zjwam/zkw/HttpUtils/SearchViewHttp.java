package com.zjwam.zkw.HttpUtils;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.entity.SearchClassBean;
import com.zjwam.zkw.search.SearchActivity;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.NetworkUtils;

import java.util.HashMap;
import java.util.Map;

public class SearchViewHttp {
    private Context context;
    private Map<String,String> params;

    public SearchViewHttp(Context context) {
        this.context = context;
    }
    public void searInfo(String classname,String id,String page){
        params = new HashMap<>();
        params.put("classname",classname);
        params.put("id",id);
        params.put("page",page);
        OkGoUtils.postRequets(Config.URL + "api/Search/search_class", context, params, new Json2Callback<SearchClassBean>() {
            @Override
            public void onSuccess(Response<SearchClassBean> response) {
                if (context instanceof SearchActivity){
                    ((SearchActivity)context).getData(response);
                }
            }

            @Override
            public void onError(Response<SearchClassBean> response) {
                super.onError(response);
                if (!NetworkUtils.isNetAvailable(context)) {
                    ((SearchActivity) context).netError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof SearchActivity){
                    ((SearchActivity) context).loadFinish();
                }
            }
        });
    }
}
