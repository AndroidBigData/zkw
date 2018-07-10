package com.zjwam.zkw.entity;

public class PersonalQDBean {
    private String code;
    private String msg;
    private JiFen data;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public JiFen getData() {
        return data;
    }

    public class JiFen{
        private String jifen;

        public String getJifen() {
            return jifen;
        }
    }
}
