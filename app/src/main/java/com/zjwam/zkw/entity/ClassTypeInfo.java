package com.zjwam.zkw.entity;

import java.io.Serializable;

public class ClassTypeInfo implements Serializable{
    private String id,name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
