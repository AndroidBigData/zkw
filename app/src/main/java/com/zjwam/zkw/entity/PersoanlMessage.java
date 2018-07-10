package com.zjwam.zkw.entity;

import java.io.Serializable;

public class PersoanlMessage implements Serializable{
    private String nickname;
    private String pic;
    private int jifen;

    public String getNickname() {
        return nickname;
    }

    public String getPic() {
        return pic;
    }

    public int getJifen() {
        return jifen;
    }
}
