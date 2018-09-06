package com.zjwam.zkw.entity;

import java.util.List;

public class ProfessionChoiceBean {
    private String name,cate;
    private long id,pid;
    private List<Child> child;

    public String getName() {
        return name;
    }

    public String getCate() {
        return cate;
    }

    public long getId() {
        return id;
    }

    public long getPid() {
        return pid;
    }

    public List<Child> getChild() {
        return child;
    }

    public class Child{
        private String name,cate;
        private long id,pid;
        private List<Child> child;

        public String getName() {
            return name;
        }

        public String getCate() {
            return cate;
        }

        public long getId() {
            return id;
        }

        public long getPid() {
            return pid;
        }

        public List<Child> getChild() {
            return child;
        }
    }
}
