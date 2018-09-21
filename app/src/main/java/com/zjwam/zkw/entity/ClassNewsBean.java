package com.zjwam.zkw.entity;

import java.util.List;

public class ClassNewsBean {
    private List<Item> newClass,teacher,goodClass,hotClass;

    public List<Item> getNewClass() {
        return newClass;
    }

    public List<Item> getTeacher() {
        return teacher;
    }

    public List<Item> getGoodClass() {
        return goodClass;
    }

    public List<Item> getHotClass() {
        return hotClass;
    }

    public class Item{
        private String img,name,grade;
        private int id,cate_id;

        public String getImg() {
            return img;
        }

        public String getName() {
            return name;
        }

        public String getGrade() {
            return grade;
        }

        public int getId() {
            return id;
        }

        public int getCate_id() {
            return cate_id;
        }
    }
}
