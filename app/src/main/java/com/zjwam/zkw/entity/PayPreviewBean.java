package com.zjwam.zkw.entity;

import java.util.List;

public class PayPreviewBean {
    private List<getOrderItems> order;

    public List<getOrderItems> getOrder() {
        return order;
    }

    public class getOrderItems{
        private String name,img,abstracts;
        private int num,alltime,id;
        private double price,old_price;

        public String getName() {
            return name;
        }

        public String getImg() {
            return img;
        }

        public String getAbstracts() {
            return abstracts;
        }

        public int getNum() {
            return num;
        }

        public int getAlltime() {
            return alltime;
        }

        public int getId() {
            return id;
        }

        public double getPrice() {
            return price;
        }

        public double getOld_price() {
            return old_price;
        }
    }
}
