package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.ProfessionChoiceBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.ProfessionChoiceModel;
import com.zjwam.zkw.mvp.model.imodel.IProfessionChoiceModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IProfessionChoicePresenter;
import com.zjwam.zkw.mvp.view.IProfessionChoiceView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfessionChoicePresenter implements IProfessionChoicePresenter {
    private Context context;
    private IProfessionChoiceView professionChoiceView;
    private IProfessionChoiceModel professionChoiceModel;
    private Map<String,String> param;

    public ProfessionChoicePresenter(Context context, IProfessionChoiceView professionChoiceView) {
        this.context = context;
        this.professionChoiceView = professionChoiceView;
        professionChoiceModel = new ProfessionChoiceModel();
    }

    @Override
    public void getProfession(String name) {
        param = new HashMap<>();
        param.put("name",name);
        professionChoiceModel.getProfession(Config.URL + "api/resume/searchJob", context, param, new BasicCallback<ResponseBean<List<ProfessionChoiceBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<ProfessionChoiceBean>>> response) {
                professionChoiceView.setProfession(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<List<ProfessionChoiceBean>>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                professionChoiceView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
