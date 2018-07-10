package com.zjwam.zkw.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 课程页面初始化时的展示数据
 */
public class CurriculumLnitializationBean implements Serializable {
private int count;
private List<ClassInfo> class_list;
private List<CateDatasBean> cate;

    public List<CateDatasBean> getCate() {
        return cate;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setClass_list(List<ClassInfo> class_list) {
        this.class_list = class_list;
    }

    public List<ClassInfo> getClass_list() {
        return class_list;
    }

    public int getCount() {
        return count;
    }



}
