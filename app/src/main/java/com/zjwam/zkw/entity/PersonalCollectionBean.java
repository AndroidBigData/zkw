package com.zjwam.zkw.entity;

import java.util.List;

public class PersonalCollectionBean {
    private List<CollectionItems> class_list;
    private int count;

    public List<CollectionItems> getClass_list() {
        return class_list;
    }

    public int getCount() {
        return count;
    }

    public class CollectionItems {
        private String name,type,img,abstracts;
        private int id,num,snum,star;

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getImg() {
            return img;
        }

        public String getAbstracts() {
            return abstracts;
        }

        public int getId() {
            return id;
        }

        public int getNum() {
            return num;
        }

        public int getSnum() {
            return snum;
        }

        public int getStar() {
            return star;
        }
    }

}
