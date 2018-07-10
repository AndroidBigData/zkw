package com.zjwam.zkw.entity;

import java.util.List;

public class PersonalOrderBean {
    private int count;
    private List<getOrderItems> pay;

    public int getCount() {
        return count;
    }

    public List<getOrderItems> getPay() {
        return pay;
    }

    public class getOrderItems {
        private String cname, orderno, price, payment, addtime, paytime;

        public String getCname() {
            return cname;
        }

        public String getOrderno() {
            return orderno;
        }

        public String getPrice() {
            return price;
        }

        public String getPayment() {
            return payment;
        }

        public String getAddtime() {
            return addtime;
        }

        public String getPaytime() {
            return paytime;
        }
    }
}
