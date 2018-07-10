package com.zjwam.zkw.entity;

import java.util.List;

public class PersonalMineAskBean {
    private int count;
    private List<getAskItems> question;

    public int getCount() {
        return count;
    }

    public List<getAskItems> getQuestion() {
        return question;
    }

    public class getAskItems{
        private String content,addtime,pic,name,nickname;
        private int id,count;

        public String getContent() {
            return content;
        }

        public String getAddtime() {
            return addtime;
        }

        public String getPic() {
            return pic;
        }

        public String getName() {
            return name;
        }

        public String getNickname() {
            return nickname;
        }

        public int getId() {
            return id;
        }

        public int getCount() {
            return count;
        }
    }
}
