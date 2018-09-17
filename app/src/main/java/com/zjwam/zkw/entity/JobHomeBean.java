package com.zjwam.zkw.entity;

import java.util.List;

public class JobHomeBean {
    private int count;
    private List<Resume> resume;

    public int getCount() {
        return count;
    }

    public List<Resume> getResume() {
        return resume;
    }

    public class Resume{
        private String job_name,create_time,company_name,salary,area,type;
        private List<Benefit> benefit;
        private long id,hold_id;

        public String getJob_name() {
            return job_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getCompany_name() {
            return company_name;
        }

        public String getSalary() {
            return salary;
        }

        public long getId() {
            return id;
        }

        public long getHold_id() {
            return hold_id;
        }

        public String getArea() {
            return area;
        }

        public String getType() {
            return type;
        }

        public List<Benefit> getBenefit() {
            return benefit;
        }
    }

    public class Benefit{
        private String type;

        public String getType() {
            return type;
        }
    }
}
