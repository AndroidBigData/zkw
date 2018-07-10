package com.zjwam.zkw.entity;

import java.io.Serializable;

public class CurriculumSelectBean implements Serializable{
    private String name;
    private int Id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
