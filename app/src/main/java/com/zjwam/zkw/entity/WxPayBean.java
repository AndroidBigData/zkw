package com.zjwam.zkw.entity;

public class WxPayBean {
    private getWxPay orderinfo;

    public getWxPay getOrderinfo() {
        return orderinfo;
    }
    public class getWxPay{
        private String appid,partnerid,prepayid,noncestr,timestamp,sign,packages;

        public String getAppid() {
            return appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getSign() {
            return sign;
        }

        public String getPackages() {
            return packages;
        }
    }
}
