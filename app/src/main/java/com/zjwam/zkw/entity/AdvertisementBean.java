package com.zjwam.zkw.entity;

public class AdvertisementBean {
    private getAdvMsg ad;

    public getAdvMsg getAd() {
        return ad;
    }

    public class getAdvMsg{
        private String img,url,title;

        public String getImg() {
            return img;
        }

        public String getUrl() {
            return url;
        }

        public String getTitle() {
            return title;
        }
    }
}
