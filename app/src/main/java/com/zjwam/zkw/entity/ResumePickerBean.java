package com.zjwam.zkw.entity;

import java.util.List;

public class ResumePickerBean {

    private List<BasicInfo> job_type,hiredate,education,nature;

    public List<BasicInfo> getJob_type() {
        return job_type;
    }

    public List<BasicInfo> getHiredate() {
        return hiredate;
    }

    public List<BasicInfo> getEducation() {
        return education;
    }

    public List<BasicInfo> getNature() {
        return nature;
    }

    public class BasicInfo{
        private long id;
        private String name;

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
