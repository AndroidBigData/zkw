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
        private int id,study_num;
        private String exam_name,cate;

        public int getId() {
            return id;
        }

        public int getStudy_num() {
            return study_num;
        }

        public String getExam_name() {
            return exam_name;
        }

        public String getCate() {
            return cate;
        }
    }
}
