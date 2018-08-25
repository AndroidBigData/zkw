package com.zjwam.zkw.entity;

import java.util.List;

public class ExamBean {
    private int count;
    private List<Exam> exam;

    public int getCount() {
        return count;
    }

    public List<Exam> getExam() {
        return exam;
    }

    public  class Exam{
        private int id,study_num,hold;
        private String exam_name,cate;

        public int getId() {
            return id;
        }

        public int getStudy_num() {
            return study_num;
        }

        public void setHold(int hold) {
            this.hold = hold;
        }

        public int getHold() {
            return hold;
        }

        public String getExam_name() {
            return exam_name;
        }

        public String getCate() {
            return cate;
        }
    }
}
