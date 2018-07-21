package com.zjwam.zkw.entity;

import java.util.List;

public class PersonalMineAskItemBean {
    private getMineAsk detial;
    private int count;

    public getMineAsk getDetial() {
        return detial;
    }

    public int getCount() {
        return count;
    }

    public class getMineAsk{
        private String content,addtime,pic,nickname;
        private int id;
        private List<getMineAskItem> answer;

        public String getContent() {
            return content;
        }

        public String getAddtime() {
            return addtime;
        }

        public String getPic() {
            return pic;
        }

        public String getNickname() {
            return nickname;
        }

        public int getId() {
            return id;
        }

        public List<getMineAskItem> getAnswer() {
            return answer;
        }
    }
    public class getMineAskItem{
        private String content,addtime,pic,nickname;
        private int id;

        public String getContent() {
            return content;
        }

        public String getAddtime() {
            return addtime;
        }

        public String getPic() {
            return pic;
        }

        public String getNickname() {
            return nickname;
        }

        public int getId() {
            return id;
        }
    }
}
