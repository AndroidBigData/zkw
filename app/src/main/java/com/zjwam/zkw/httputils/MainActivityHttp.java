package com.zjwam.zkw.httputils;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.MainActivity;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.entity.ClassSearchBean;
import com.zjwam.zkw.entity.CurriculumLnitializationBean;
import com.zjwam.zkw.entity.HomePageBean;
import com.zjwam.zkw.entity.PersoanlMessage;
import com.zjwam.zkw.entity.PersonalQDBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.NetworkUtils;

public class MainActivityHttp {
    private Context context;
    private boolean isInitCache = false;
    public MainActivityHttp(Context context) {
        this.context = context;
    }

    /**
     * 主页
     */
    public void getHomePageData(){
        OkGo.<HomePageBean>get(Config.URL+"api/Index/index?type=android")
                .tag(context)
                .cacheKey("HomePageFragment")
                .execute(new Json2Callback<HomePageBean>() {
                    @Override
                    public void onSuccess(Response<HomePageBean> response) {
                        if (context instanceof MainActivity){
                            ((MainActivity) context).loadData(response);
                        }
                    }
                    @Override
                    public void onCacheSuccess(Response<HomePageBean> response) {
                        super.onCacheSuccess(response);
                        if (!isInitCache) {
                            onSuccess(response);
                            isInitCache = true;
                        }
                    }

                    @Override
                    public void onError(Response<HomePageBean> response) {
                        super.onError(response);
                        if (!NetworkUtils.isNetAvailable(context)) {
                            ((MainActivity) context).error("网络不可用！");
                        }
                    }
                });
    }

    /**
     * 课程
     */

    public void getChoiceData(String wid){
        OkGoUtils.getRequets(Config.URL + "api/search/cate_search?wid=" + wid, context, new Json2Callback<ClassSearchBean>() {
            @Override
            public void onSuccess(Response<ClassSearchBean> response) {
                if (context instanceof MainActivity){
                    ((MainActivity) context).getChoiceData(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MainActivity){
                    ((MainActivity) context).getChoiceDatFinish();
                }
            }
        });
    }

    public void getChoiceListData(String id){
        OkGoUtils.getRequets(Config.URL + "api/Search/cate_search_class?id=" + id, context, new Json2Callback<CurriculumLnitializationBean>() {
            @Override
            public void onSuccess(Response<CurriculumLnitializationBean> response) {
                if (context instanceof MainActivity){
                    ((MainActivity) context).getChoiceListData(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MainActivity){
                    ((MainActivity) context).getChoiceListDataFinish();
                }
            }
        });
    }
    public void getData(String wid, int page) {
        OkGoUtils.getRequets(Config.URL + "api/course/class_index?wid=" + wid + "&page=" + page, context, new Json2Callback<CurriculumLnitializationBean>() {
            @Override
            public void onSuccess(Response<CurriculumLnitializationBean> response) {
                if (context instanceof MainActivity){
                    ((MainActivity) context).getData(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MainActivity){
                    ((MainActivity) context).getDataFinish();
                }
            }
        });
    }
    public void getInitialization() {
        OkGo.<CurriculumLnitializationBean>get(Config.URL + "api/course/class_index")
                .tag(context)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("getInitialization")
                .execute(new Json2Callback<CurriculumLnitializationBean>() {
                    @Override
                    public void onSuccess(Response<CurriculumLnitializationBean> response) {
                        if (context instanceof MainActivity){
                            ((MainActivity) context).getInitialization(response);
                        }
                    }
                    @Override
                    public void onCacheSuccess(Response<CurriculumLnitializationBean> response) {
                        super.onCacheSuccess(response);
                        onSuccess(response);
                    }
                });
    }
    /**
     * 个人中心
     */
    public void getPersonalMessage(String uid) {
        OkGo.<PersoanlMessage>get(Config.URL + "api/user/index?uid=" + uid)
                .tag(context)
                .cacheKey("MineFragment")
                .execute(new Json2Callback<PersoanlMessage>() {
                    @Override
                    public void onCacheSuccess(Response<PersoanlMessage> response) {
                        if (!isInitCache) {
                            onSuccess(response);
                            isInitCache = true;
                        }
                    }
                    @Override
                    public void onSuccess(Response<PersoanlMessage> response) {
                        if (context instanceof MainActivity){
                            ((MainActivity) context).getPersonalMessage(response);
                        }
                    }

                });
    }

    public void qdMessage(String uid) {
        OkGoUtils.getRequets(Config.URL + "api/user/sign?id=" + uid, context, new Json2Callback<PersonalQDBean>() {
            @Override
            public void onSuccess(Response<PersonalQDBean> response) {
                if (context instanceof MainActivity){
                    ((MainActivity) context).qdMessage(response);
                }
            }

            @Override
            public void onError(Response<PersonalQDBean> response) {
                super.onError(response);
                if (context instanceof MainActivity){
                    ((MainActivity) context).qdMessageError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof MainActivity){
                    ((MainActivity) context).qdMessageFinish();
                }
            }
        });
    }

}
