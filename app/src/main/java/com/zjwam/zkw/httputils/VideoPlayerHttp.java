package com.zjwam.zkw.httputils;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.Json2Callback;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.CommentBean;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.IntroduceBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.VideoAskAnswerBean;
import com.zjwam.zkw.entity.VideoCatalogBean;
import com.zjwam.zkw.entity.VideoMainMsgBean;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.videoplayer.Video2PlayActivity;

import java.util.HashMap;
import java.util.Map;

public class VideoPlayerHttp {
    private Context context;
    private Map<String,String> map;

    public VideoPlayerHttp(Context context) {
        this.context = context;
    }
    public void getFirstVideo (String id,String uid){
        map = new HashMap<>();
        map.put("id",id);
        map.put("uid",uid);
        OkGoUtils.postRequets(Config.URL + "api/play/get_first_video", context, map, new Json2Callback<VideoMainMsgBean>() {
            @Override
            public void onSuccess(Response<VideoMainMsgBean> response) {
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).getFistSuccess(response);
                }
            }
            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).getFirstFinish();
                }
            }
        });
    }
    public void refreshCar (String id,String uid){
        map = new HashMap<>();
        map.put("id",id);
        map.put("uid",uid);
        OkGoUtils.postRequets(Config.URL + "api/play/get_first_video", context, map, new Json2Callback<VideoMainMsgBean>() {
            @Override
            public void onSuccess(Response<VideoMainMsgBean> response) {
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).refreshCar(response);
                }
            }
        });
    }
    public void setSCMsg (String id,String uid){
        map = new HashMap<>();
        map.put("id",id);
        map.put("uid",uid);
        OkGoUtils.postRequets(Config.URL + "api/user/run_hold", context, map, new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).getSCSuccess(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                super.onError(response);
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).getSCError(response);
                }
            }
        });
    }
    public void setShopCard (String id,String uid){
        map = new HashMap<>();
        map.put("id",id);
        map.put("uid",uid);
        OkGoUtils.postRequets(Config.URL + "api/user/car_add", context, map, new JsonCallback<ResponseBean<EmptyBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EmptyBean>> response) {
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).getAddShopSuccess(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<EmptyBean>> response) {
                super.onError(response);
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).getAddShopError(response);
                }
            }
        });
    }

    public void getIntroduceFragment (String id){
        map = new HashMap<>();
        map.put("id",id);
        OkGoUtils.postRequets(Config.URL + "api/play/intro", context, map, new Json2Callback<IntroduceBean>() {
            @Override
            public void onSuccess(Response<IntroduceBean> response) {
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).getIntroduceIfo(response);
                }
            }
        });
    }

    public void getVideoCatalog(String id,String uid){
        map = new HashMap<>();
        map.put("id",id);
        map.put("uid",uid);
        OkGoUtils.postRequets(Config.URL + "api/play/index", context, map, new Json2Callback<VideoCatalogBean>() {
            @Override
            public void onSuccess(Response<VideoCatalogBean> response) {
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).getVideoCatelog(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).getVideoCatelogFinish();
                }
            }
        });
    }
    public void getVideoComment(String id,String page){
        map = new HashMap<>();
        map.put("id",id);
        map.put("page",page);
        OkGoUtils.postRequets(Config.URL + "api/play/comment", context, map, new Json2Callback<CommentBean>() {
            @Override
            public void onSuccess(Response<CommentBean> response) {
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).getVideoComment(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).getVideoCommentFinish();
                }
            }
        });
    }
    public void getVideoAnswer (String id,int page){
        map = new HashMap<>();
        map.put("id",id);
        map.put("page", String.valueOf(page));
        OkGoUtils.postRequets(Config.URL + "api/play/question", context, map, new JsonCallback<ResponseBean<VideoAskAnswerBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<VideoAskAnswerBean>> response) {
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).getVideoAnswer(response);
                }
            }

            @Override
            public void onError(Response<ResponseBean<VideoAskAnswerBean>> response) {
                super.onError(response);
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).getVideoAnswerError(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (context instanceof Video2PlayActivity){
                    ((Video2PlayActivity) context).getVideoAnswerFinish();
                }
            }
        });
    }
}
