package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.TeacherMoreBean;

import java.util.Map;

public interface ITeacherMoreModel {
    void getInfo(String url, Object context, Map<String,String> param, BasicCallback<ResponseBean<TeacherMoreBean>> basicCallback);
}
