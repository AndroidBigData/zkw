package com.zjwam.zkw.entity;

import java.io.Serializable;
import java.util.List;

public class ExamDetailsBean implements Serializable {
    private int flag;
    private long id;
    private String content,question_select;
    private List<Options> options;

    public int getFlag() {
        return flag;
    }

    public void setQuestion_select(String question_select) {
        this.question_select = question_select;
    }

    public String getQuestion_select() {
        return question_select;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public List<Options> getOptions() {
        return options;
    }

    public class Options implements Serializable{
        private String content;

        public String getContent() {
            return content;
        }
    }


    /**
     * 单项选择题
     */
    public static final int TYPE_Single_Choice = 2;
    /**
     * 判断题
     */
    public static final int TYPE_True_OR_False = 0;
    /**
     * 多项选择题
     */
    public static final int TYPE_Multiple_Choice = 3;
    public static String getQuestionTypeStr(int type){
        switch (type){
            case TYPE_Single_Choice:
                return "单选题";
            case TYPE_True_OR_False:
                return "判断题";
            case TYPE_Multiple_Choice:
                return "多选题";
        }
        return "单选题";
    }
}
