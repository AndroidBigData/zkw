package com.zjwam.zkw.entity;

import java.util.List;

public class ExamResultBean {
    private String answer,content,analyze;
    private long id;
    private int flag,isright;
    private List<Options> options;
    private List<Uanswer> uanswer;

    public String getAnswer() {
        return answer;
    }

    public String getContent() {
        return content;
    }

    public String getAnalyze() {
        return analyze;
    }

    public long getId() {
        return id;
    }

    public int getFlag() {
        return flag;
    }

    public int getIsright() {
        return isright;
    }

    public List<Options> getOptions() {
        return options;
    }

    public List<Uanswer> getUanswer() {
        return uanswer;
    }

    public class Options{
        private String content;

        public String getContent() {
            return content;
        }
    }

    public class Uanswer{
        private String content;

        public String getContent() {
            return content;
        }
    }
}
