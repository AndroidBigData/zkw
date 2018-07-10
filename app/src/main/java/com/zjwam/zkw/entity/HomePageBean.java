package com.zjwam.zkw.entity;

import java.io.Serializable;
import java.util.List;

public class HomePageBean implements Serializable {
    private List<LunboBean> banner;
    private List<HomePageKCTJInfo> class_es;
    private List<ClassInfo> class_list;
    private HomePageKCTJInfo yjs;

    public List<LunboBean> getBanner() {
        return banner;
    }

    public List<HomePageKCTJInfo> getClass_es() {
        return class_es;
    }

    public List<ClassInfo> getClass_list() {
        return class_list;
    }

    public HomePageKCTJInfo getYjs() {
        return yjs;
    }
}
