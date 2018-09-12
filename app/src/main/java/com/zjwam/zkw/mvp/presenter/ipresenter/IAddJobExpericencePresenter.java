package com.zjwam.zkw.mvp.presenter.ipresenter;

public interface IAddJobExpericencePresenter {
    void getChoiceInfo();
    void saveExpericence(int type,String resume_id,String work_company,String begin_time,String end_time,String nature,String work_job,String high_money,String content);
}
