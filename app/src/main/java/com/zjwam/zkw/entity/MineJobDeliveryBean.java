package com.zjwam.zkw.entity;

import java.util.List;

public class MineJobDeliveryBean {
    private int count;
    private List<Resume> resume;

    public int getCount() {
        return count;
    }

    public List<Resume> getResume() {
        return resume;
    }

    public class Resume{
        private String type,create_time,job_name,company_name,salary,area,sty;
        private long id;
        private List<Benefit> benefit;

        public String getType() {
            return type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getJob_name() {
            return job_name;
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

        public String getArea() {
            return area;
        }

        public String getSty() {
            return sty;
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
