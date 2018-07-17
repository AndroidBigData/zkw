package com.zjwam.zkw.entity;

import java.util.List;

public class VideoCatalogBean {
    private String name,msg;
    private int id,code;
    private List<ClassBean> video;

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }

    public int getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public List<ClassBean> getVideo() {
        return video;
    }
}
