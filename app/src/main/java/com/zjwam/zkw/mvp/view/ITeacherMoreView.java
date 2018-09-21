package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.TeacherMoreBean;

import java.util.List;

public interface ITeacherMoreView {
    void setInfo(TeacherMoreBean.Teacher teacher, List<TeacherMoreBean.Classfor> classfor);
    void showMsg(String msg);
}
