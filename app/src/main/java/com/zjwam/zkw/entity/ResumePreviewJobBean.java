package com.zjwam.zkw.entity;

public class ResumePreviewJobBean {
    private long id;
    private String work_company,work_company_nature,begin_time,end_time,work_job,responsibility;
    private int work_emolument_high;

    public long getId() {
        return id;
    }

    public String getWork_company() {
        return work_company;
    }

    public String getWork_company_nature() {
        return work_company_nature;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getWork_job() {
        return work_job;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public int getWork_emolument_high() {
        return work_emolument_high;
    }
}
