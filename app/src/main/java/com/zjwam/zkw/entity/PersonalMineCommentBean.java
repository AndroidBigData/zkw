package com.zjwam.zkw.entity;

import java.util.List;

public class PersonalMineCommentBean {
    private int count;
    private List<getCommentItems> comment;

    public int getCount() {
        return count;
    }

    public List<getCommentItems> getComment() {
        return comment;
    }

    public class getCommentItems{
        private String content,addtime,name;
        private int star;

        public String getContent() {
            return content;
        }

        public String getAddtime() {
            return addtime;
        }

        public String getName() {
            return name;
        }

        public int getStar() {
            return star;
        }
    }
}
