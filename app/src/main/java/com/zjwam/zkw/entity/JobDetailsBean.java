package com.zjwam.zkw.entity;

import java.util.List;

public class JobDetailsBean {
    private String job_name,create_time,company_name,work_area,intro,requirement,salary,type
            ,address,company_type,logo;
    private long id,company_id;
    private int hold,count;
    private List<Benefit> benefit;
    private List<Recommend> recommend;

    public String getJob_name() {
        return job_name;
    }

    public long getCompany_id() {
        return company_id;
    }

    public String getLogo() {
        return logo;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getWork_area() {
        return work_area;
    }

    public String getIntro() {
        return intro;
    }

    public String getRequirement() {
        return requirement;
    }

    public String getSalary() {
        return salary;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getCompany_type() {
        return company_type;
    }

    public int getCount() {
        return count;
    }

    public List<Benefit> getBenefit() {
        return benefit;
    }

    public List<Recommend> getRecommend() {
        return recommend;
    }

    public int getHold() {
        return hold;
    }

    public class Benefit{
        private String type;

        public String getType() {
            return type;
        }
    }
    public class Recommend{
        private String job_name,create_time,salary,type,company_name;
        private long id;
        private List<Benefit> benefit;

        public String getJob_name() {
            return job_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getSalary() {
            return salary;
        }

        public String getType() {
            return type;
        }

        public long getId() {
            return id;
        }

        public List<Benefit> getBenefit() {
            return benefit;
        }

        public String getCompany_name() {
            return company_name;
        }
    }
}
