package com.zjwam.zkw.entity;

import java.util.List;

public class MineShopCartBean {
    private List<getShopCartItems> car;

    public List<getShopCartItems> getCar() {
        return car;
    }

    public class getShopCartItems{
        private String name,img,alltime,price;
        private int clid,id,num;
        private boolean isChecked;

        public int getClid() {
            return clid;
        }

        public String getName() {
            return name;
        }

        public String getImg() {
            return img;
        }

        public int getId() {
            return id;
        }

        public int getNum() {
            return num;
        }

        public String getAlltime() {
            return alltime;
        }

        public String getPrice() {
            return price;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
