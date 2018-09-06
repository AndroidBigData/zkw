package com.zjwam.zkw.entity;

import java.util.List;

public class IndustryChoiceBean {
    private String name,cate;
    private long id,pid;
    private List<IndustryChoiceBean> child;

    public String getName() {
        return name;
    }

    public String getCate() {
        return cate;
    }

    public long getId() {
        return id;
    }

    public long getPid() {
        return pid;
    }

    public List<IndustryChoiceBean> getChild() {
        return child;
    }
}
