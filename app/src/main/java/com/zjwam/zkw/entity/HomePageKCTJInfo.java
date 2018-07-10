package com.zjwam.zkw.entity;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.List;

public class HomePageKCTJInfo implements Serializable{
private String name,code;
private List<HomePageKCTJItemInfo> cate;
private List<HomePageKCTJItemImgs> app;

    public List<HomePageKCTJItemImgs> getItemImgs() {
        return app;
    }

    public void setItemImgs(List<HomePageKCTJItemImgs> itemImgs) {
        this.app = itemImgs;
    }

    public List<HomePageKCTJItemInfo> getItemInfos() {
        return cate;
    }

    public void setItemInfos(List<HomePageKCTJItemInfo> itemInfos) {
        this.cate = itemInfos;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



}
