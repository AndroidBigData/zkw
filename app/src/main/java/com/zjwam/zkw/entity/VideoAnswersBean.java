package com.zjwam.zkw.entity;

import java.util.List;

public class VideoAnswersBean {
    private int count;
    private getQuestion question;
    private List<getAnswers> child;

    public int getCount() {
        return count;
    }

    public getQuestion getQuestion() {
        return question;
    }

    public List<getAnswers> getChild() {
        return child;
    }

    public class getQuestion{
        private String content,addtime,nick,pic;
        private int id;

        public String getContent() {
            return content;
        }

        public String getAddtime() {
            return addtime;
        }

        public String getNick() {
            return nick;
        }

        public String getPic() {
            return pic;
        }

        public int getId() {
            return id;
        }
    }
    public class getAnswers{
        private String content,addtime,nick,pic;
        private int id;

        public String getContent() {
            return content;
        }

        public String getAddtime() {
            return addtime;
        }

        public String getNick() {
            return nick;
        }

        public String getPic() {
            return pic;
        }

        public int getId() {
            return id;
        }
    }
}
