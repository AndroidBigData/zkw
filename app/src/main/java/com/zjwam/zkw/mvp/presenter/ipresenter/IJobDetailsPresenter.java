package com.zjwam.zkw.mvp.presenter.ipresenter;

public interface IJobDetailsPresenter {
    void getJobDetails(String id);
    void holdJob(String jobId,String companyId);
}
