package com.zjwam.zkw.entity;

import java.util.List;

public class ExamTestCollectionBean {
    private int count;
    private List<Paper> paper;

    public int getCount() {
        return count;
    }

    public List<Paper> getPaper() {
        return paper;
    }

    public class Paper{
        private String addtime,name,content,answer,analyze;
        private List<Options> options;
        private int flag;
        private boolean isOpen;

        public String getAddtime() {
            return addtime;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }

        public String getAnswer() {
            return answer;
        }

        public String getAnalyze() {
            return analyze;
        }

        public List<Options> getOptions() {
            return options;
        }

        public int getFlag() {
            return flag;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean open) {
            isOpen = open;
        }
    }
    public class Options{
        private String content;

        public String getContent() {
            return content;
        }
    }
}
