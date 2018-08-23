package com.zjwam.zkw.mvp.presenter.ipresenter;

import org.json.JSONObject;

public interface IExamDetailsPresenter {
    void getExamDetails(String id);
    void upExam(String id,JSONObject json);
}
