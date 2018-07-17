package com.zjwam.zkw.entity;

import java.util.List;

public class SearchClassBean {
    private List<ClassInfo> class_list;
    private List<ClassTypeInfo> web;
    private int count;

    public List<ClassInfo> getClass_list() {
        return class_list;
    }

    public List<ClassTypeInfo> getWeb() {
        return web;
    }

    public int getCount() {
        return count;
    }
}
