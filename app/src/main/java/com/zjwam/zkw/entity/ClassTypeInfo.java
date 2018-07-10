package com.zjwam.zkw.entity;

import java.io.Serializable;

public class ClassTypeInfo implements Serializable{
    private String webid,webname;

    public String getWebid() {
        return webid;
    }

    public void setWebid(String webid) {
        this.webid = webid;
    }

    public String getWebname() {
        return webname;
    }

    public void setWebname(String webname) {
        this.webname = webname;
    }
}
