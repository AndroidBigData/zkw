package com.zjwam.zkw.entity;

import java.io.Serializable;

public class IntroduceBean implements Serializable{
    private String name,abstracts,intro;
    private int num,snum,star,buynum;

    public String getName() {
        return name;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public String getIntro() {
        return intro;
    }

    public int getNum() {
        return num;
    }

    public int getSnum() {
        return snum;
    }

    public int getStar() {
        return star;
    }

    public int getBuynum() {
        return buynum;
    }
}
