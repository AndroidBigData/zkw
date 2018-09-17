package com.zjwam.zkw.entity;

import java.util.List;

public class JobEmplomentBean {
    private int count;
    private List<Guide> guide;

    public int getCount() {
        return count;
    }

    public List<Guide> getGuide() {
        return guide;
    }

    public class Guide{
        private String title,title_img,create_time,company,url;
        private int pv;
        private long id;

        public String getTitle() {
            return title;
        }

        public String getTitle_img() {
            return title_img;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getCompany() {
            return company;
        }

        public String getUrl() {
            return url;
        }

        public int getPv() {
            return pv;
        }

        public long getId() {
            return id;
        }
    }
}
