package com.zjwam.zkw.entity;

import java.util.List;

public class VideoAskAnswerBean {
    private int count;
    private List<getVideoAnswerItems> question;

    public int getCount() {
        return count;
    }

    public List<getVideoAnswerItems> getQuestion() {
        return question;
    }

    public class getVideoAnswerItems{
        private String content,addtime,nick,pic;
        private int id,answer;

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

        public int getAnswer() {
            return answer;
        }
    }
}
