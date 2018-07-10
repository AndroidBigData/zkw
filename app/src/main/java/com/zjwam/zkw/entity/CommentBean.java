package com.zjwam.zkw.entity;

import java.io.Serializable;
import java.util.List;

public class CommentBean implements Serializable{
    private  int count,star;
    private List<Comment> comment;

    public int getStar() {
        return star;
    }

    public int getCount() {
        return count;
    }

    public List<Comment> getComment() {
        return comment;
    }

   public class Comment{
        private String content,nick,pic;
        private int star;
        private String addtime;

        public String getContent() {
            return content;
        }

        public String getNick() {
            return nick;
        }

        public String getPic() {
            return pic;
        }

        public int getStar() {
            return star;
        }

        public String getAddtime() {
            return addtime;
        }
    }
}
