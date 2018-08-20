package com.zjwam.zkw.entity;

import java.io.Serializable;

public class LunboBean implements Serializable{
    private String img,url;
    private int clid;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getClid() {
        return clid;
    }

    public void setClid(int clid) {
        this.clid = clid;
    }

    public String getUrl() {
        return url;
    }
}
