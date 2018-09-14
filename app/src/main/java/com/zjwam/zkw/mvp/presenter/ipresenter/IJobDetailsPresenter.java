package com.zjwam.zkw.mvp.presenter.ipresenter;

public interface IJobDetailsPresenter {
    void getJobDetails(String id);
    void holdJob(String jobId,String companyId);
    void getResume();
    void sendResume(String rid,String jid,String cid);
}
