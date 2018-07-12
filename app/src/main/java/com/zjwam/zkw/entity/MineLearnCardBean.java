package com.zjwam.zkw.entity;

import java.util.List;

public class MineLearnCardBean {
    private getLearnCardItems card;
    private getBugMsg ad;

    public getBugMsg getAd() {
        return ad;
    }

    public getLearnCardItems getCard() {
        return card;
    }

    public class getLearnCardItems{
        private List<getLearnCard> end;
        private List<getLearnCard> start;
        private List<getLearnCard> on;

        public List<getLearnCard> getEnd() {
            return end;
        }

        public List<getLearnCard> getStart() {
            return start;
        }

        public List<getLearnCard> getOn() {
            return on;
        }
    }

    public class getLearnCard {
        private String card_num,card_pwd,overtime,username,img;
        private int id,type;

        public String getCard_num() {
            return card_num;
        }

        public String getCard_pwd() {
            return card_pwd;
        }

        public String getOvertime() {
            return overtime;
        }

        public String getUsername() {
            return username;
        }

        public int getId() {
            return id;
        }

        public int getType() {
            return type;
        }

        public String getImg() {
            return img;
        }
    }
    public class getBugMsg{
        private String url,title;

        public String getUrl() {
            return url;
        }

        public String getTitle() {
            return title;
        }
    }
}
