package com.zjwam.zkw.entity;

import java.util.List;

public class TeacherMoreBean {
    private Teacher teacher;
    private List<Classfor> classfor;

    public Teacher getTeacher() {
        return teacher;
    }

    public List<Classfor> getClassfor() {
        return classfor;
    }

    public class Teacher{
        private String img,name,sign,info,honor;
        private List<String> honor_img;

        public String getImg() {
            return img;
        }

        public String getName() {
            return name;
        }

        public String getSign() {
            return sign;
        }

        public String getInfo() {
            return info;
        }

        public String getHonor() {
            return honor;
        }

        public List<String> getHonor_img() {
            return honor_img;
        }
    }
    public class Classfor{
        private String name,img,bub;
        private long id;

        public String getName() {
            return name;
        }

        public String getImg() {
            return img;
        }

        public String getBub() {
            return bub;
        }

        public long getId() {
            return id;
        }
    }
}
