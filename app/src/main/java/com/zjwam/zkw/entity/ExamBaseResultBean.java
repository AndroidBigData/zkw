package com.zjwam.zkw.entity;

import java.util.List;

public class ExamBaseResultBean {
    private String rate,time;
    private int rnum,wnum;
    private List<ExamResultBean> question;

    public String getRate() {
        return rate;
    }

    public String getTime() {
        return time;
    }

    public int getRnum() {
        return rnum;
    }

    public int getWnum() {
        return wnum;
    }

    public List<ExamResultBean> getQuestion() {
        return question;
    }
}
