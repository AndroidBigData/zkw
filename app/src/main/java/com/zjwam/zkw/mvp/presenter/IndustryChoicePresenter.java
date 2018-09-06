package com.zjwam.zkw.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.callback.BasicCallback;
import com.zjwam.zkw.entity.IndustryChoiceBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.httputils.HttpErrorMsg;
import com.zjwam.zkw.mvp.model.IndustryChoiceModel;
import com.zjwam.zkw.mvp.model.imodel.IIndustryChoiceModel;
import com.zjwam.zkw.mvp.presenter.ipresenter.IIndustryChoicePresenter;
import com.zjwam.zkw.mvp.view.IIndustryChoiceView;
import com.zjwam.zkw.util.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndustryChoicePresenter implements IIndustryChoicePresenter {
    private Context context;
    private IIndustryChoiceView industryChoiceView;
    private IIndustryChoiceModel industryChoiceModel;
    private Map<String,String> param;

    public IndustryChoicePresenter(Context context, IIndustryChoiceView industryChoiceView) {
        this.context = context;
        this.industryChoiceView = industryChoiceView;
        industryChoiceModel = new IndustryChoiceModel();
    }

    @Override
    public void getIdustry(String name) {
        param = new HashMap<>();
        param.put("name",name);
        industryChoiceModel.getIndustry(Config.URL + "api/resume/searchHangye", context,param, new BasicCallback<ResponseBean<List<IndustryChoiceBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<IndustryChoiceBean>>> response) {
                industryChoiceView.setIndustry(response.body().data);
            }

            @Override
            public void onError(Response<ResponseBean<List<IndustryChoiceBean>>> response) {
                Throwable exception = response.getException();
                String error = HttpErrorMsg.getErrorMsg(exception);
                industryChoiceView.showMsg(error);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
