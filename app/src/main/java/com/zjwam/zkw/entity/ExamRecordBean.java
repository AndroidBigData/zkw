package com.zjwam.zkw.entity;

import java.util.List;

public class ExamRecordBean {
    private int count;
    private List<Exam> exam;

    public int getCount() {
        return count;
    }

    public List<Exam> getRecord() {
        return exam;
    }

    public class Exam{
        private String exam_name,addtime,cate;
        private List<Detial> detial;
        private long id;
        private boolean isOpen;

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean open) {
            isOpen = open;
        }

        public String getExam_name() {
            return exam_name;
        }

        public String getAddtime() {
            return addtime;
        }

        public String getCate() {
            return cate;
        }

        public List<Detial> getDetial() {
            return detial;
        }

        public long getId() {
            return id;
        }
    }
    public class Detial{
        private String name,rate;
        private long id,eid;
        private int rnum,wnum;

        public String getName() {
            return name;
        }

        public String getRate() {
            return rate;
        }

        public long getId() {
            return id;
        }

        public long getEid() {
            return eid;
        }

        public int getRnum() {
            return rnum;
        }

        public int getWnum() {
            return wnum;
        }
    }
}
