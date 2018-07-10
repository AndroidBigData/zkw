package com.zjwam.zkw.entity;

import java.util.List;

public class MineIntegralBean {
    private int allfen, count;
    private List<getIntegralItem> jifen;

    public int getAllfen() {
        return allfen;
    }

    public int getCount() {
        return count;
    }

    public List<getIntegralItem> getJifen() {
        return jifen;
    }

    public class getIntegralItem {
        private String method, fen, addtime;

        public String getMethod() {
            return method;
        }

        public String getFen() {
            return fen;
        }

        public String getAddtime() {
            return addtime;
        }
    }
}
