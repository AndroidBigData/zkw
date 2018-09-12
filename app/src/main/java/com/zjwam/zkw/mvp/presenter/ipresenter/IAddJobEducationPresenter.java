package com.zjwam.zkw.mvp.presenter.ipresenter;

public interface IAddJobEducationPresenter {
    void getEducaion();
    void saveEducation(int type,String resume_id,String school,String school_nature,String professional,String begin_time,String end_time);
}
