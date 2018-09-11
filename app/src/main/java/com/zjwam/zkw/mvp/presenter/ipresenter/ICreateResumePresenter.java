package com.zjwam.zkw.mvp.presenter.ipresenter;

public interface ICreateResumePresenter {
    void getResumeChoiceInfo();
    void getProfession();
    void saveResume(String title,String job_type,String industry_id,String hiredate,String low_money,String high_money,String name,String gender,
                    String age,String phone,String evaluate,String skill);
}
