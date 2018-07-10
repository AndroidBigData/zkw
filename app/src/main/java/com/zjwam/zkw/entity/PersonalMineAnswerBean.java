package com.zjwam.zkw.entity;

import java.util.List;

public class PersonalMineAnswerBean {
    private int count;
    private List<getAnswerItems> answer;

    public int getCount() {
        return count;
    }

    public List<getAnswerItems> getAnswer() {
        return answer;
    }

    public class getAnswerItems{
        private String content,addtime,pic,name,nickname;
        private int id;
        private getQuestion question;

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

        public getQuestion getQuestion() {
            return question;
        }
    }
    public class getQuestion{
        private String content,addtime,nickname;

        public String getContent() {
            return content;
        }

        public String getAddtime() {
            return addtime;
        }

        public String getNickname() {
            return nickname;
        }
    }
}
