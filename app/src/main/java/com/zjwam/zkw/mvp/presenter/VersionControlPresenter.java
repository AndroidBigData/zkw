package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.VersionBean;
import com.zjwam.zkw.mvp.model.VersionControlModel;
import com.zjwam.zkw.mvp.model.imodel.IVersionControlModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IVersionControlPresenter;
import com.zjwam.zkw.mvp.view.IVersionControlView;
import com.zjwam.zkw.util.Config;

public class VersionControlPresenter implements IVersionControlPresenter {
    private Context context;
    private IVersionControlView versionControlView;
    private IVersionControlModel versionControlModel;

    public VersionControlPresenter(Context context, IVersionControlView versionControlView) {
        this.context = context;
        this.versionControlView = versionControlView;
        versionControlModel = new VersionControlModel();
    }

    @Override
    public void versionControl() {
        versionControlModel.versionControl(Config.URL + "api/index/version?type=android", context, new BasicCallback<ResponseBean<VersionBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<VersionBean>> response) {
                VersionBean versionBean = response.body().data;
                versionControlView.versinonControl(versionBean);
            }

            @Override
            public void onError(Response<ResponseBean<VersionBean>> response) {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
