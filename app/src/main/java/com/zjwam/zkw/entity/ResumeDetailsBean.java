package com.zjwam.zkw.entity;

import java.util.List;

public class ResumeDetailsBean {
    private long id;
    private String resume_name,user_name,gender,phone,hiredate,job_type,industry_id,evaluate,skill,salary;
    private int age;
    private List<Other> other;

    public long getId() {
        return id;
    }

    public String getResume_name() {
        return resume_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getHiredate() {
        return hiredate;
    }

    public String getJob_type() {
        return job_type;
    }

    public String getIndustry_id() {
        return industry_id;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public String getSkill() {
        return skill;
    }

    public String getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    public List<Other> getOther() {
        return other;
    }

    public class Other{
        private int type;
        private String type_msg;
        private long id;

        public int getType() {
            return type;
        }

        public String getType_msg() {
            return type_msg;
        }

        public long getId() {
            return id;
        }
    }
}
