package com.zjwam.zkw.entity;

import java.util.List;

public class SearchJobDetailsPopBean {
    private List<BasicBean> area;
    private List<BasicBean> education;
    private List<BasicBean> workyear;
    private List<BasicBean> money;

    public List<BasicBean> getArea() {
        return area;
    }

    public List<BasicBean> getEducation() {
        return education;
    }

    public List<BasicBean> getWorkyear() {
        return workyear;
    }

    public List<BasicBean> getMoney() {
        return money;
    }

    public class BasicBean{
        private long id;
        private String name;

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
