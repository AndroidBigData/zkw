package com.zjwam.zkw.mvp.presenter.ipresenter;

public interface IAddJobProjectPresenter {
    void saveProject(int type,String resume_id,String project_name,String begin_time,String end_time,String intro,String content);
}
