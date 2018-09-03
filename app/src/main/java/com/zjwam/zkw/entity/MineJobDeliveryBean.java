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
        private String type,create_time,job_name,company_name,salary;
        private long id;

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
    }
}
