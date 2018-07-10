package com.zjwam.zkw.entity;

import java.util.List;

public class PersonalNoteBookBean {
    private int count;
    private List<getNoteBookItem> note;

    public int getCount() {
        return count;
    }

    public List<getNoteBookItem> getNote() {
        return note;
    }

    public class getNoteBookItem{
        private int id,sort;
        private String vtime,note,addtime,name,vname;

        public int getId() {
            return id;
        }

        public int getSort() {
            return sort;
        }

        public String getVtime() {
            return vtime;
        }

        public String getNote() {
            return note;
        }

        public String getAddtime() {
            return addtime;
        }

        public String getName() {
            return name;
        }

        public String getVname() {
            return vname;
        }
    }
}
