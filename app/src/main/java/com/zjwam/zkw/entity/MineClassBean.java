package com.zjwam.zkw.entity;

import java.util.List;

public class MineClassBean {
private String code;
private String msg;
private getClass data;

    public getClass getData() {
        return data;
    }

    public class getClass{
    private List<itemBean> class_list;
    private int count;

        public int getCount() {
            return count;
        }

        public List<itemBean> getClass_list() {
        return class_list;
    }
}



    public class itemBean{
    private int id,ctime,begin,num;
    private String name,abstracts,ratio,type,img;
    public int getId() {
        return id;
    }

    public int getCtime() {
        return ctime;
    }

    public int getBegin() {
        return begin;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public String getRatio() {
        return ratio;
    }

    public String getType() {
        return type;
    }

    public String getImg() {
        return img;
    }
}



}
