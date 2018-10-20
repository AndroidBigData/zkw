package com.zjwam.zkw.mvp.model.imodel;

import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.VersionBean;

import java.util.Map;

public interface IVersionControlModel {
    void versionControl(String url, Object context, BasicCallback<ResponseBean<VersionBean>> basicCallback);
}
