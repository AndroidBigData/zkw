package com.zjwam.zkw.entity;

import java.util.List;

public class CollectionExamBean {
    private int count;
    private List<Hold> hold;

    public int getCount() {
        return count;
    }

    public List<Hold> getHold() {
        return hold;
    }

    public class Hold{
        private String addtime,cate,holdtime,name;
        // id:收藏；eid：试卷；clid：课程
        private long id,eid,clid;

        public String getAddtime() {
            return addtime;
        }

        public String getCate() {
            return cate;
        }

        public String getHoldtime() {
            return holdtime;
        }

        public String getName() {
            return name;
        }

        public long getId() {
            return id;
        }

        public long getEid() {
            return eid;
        }

        public long getClid() {
            return clid;
        }
    }
}
