package com.zjwam.zkw.jsondata;

import com.zjwam.zkw.entity.ClassBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoJson2Data {
    private String result;

    public VideoJson2Data(String result) {
        this.result = result;
    }

    public List<ClassBean> getClassItem (){
        List<ClassBean> classItems = new ArrayList<>();
        try {
            JSONArray items = new JSONObject(result).getJSONArray("video");
            for (int i = 0 ; i < items.length() ; i++){
                ClassBean classBean = new ClassBean();
                JSONObject item = items.getJSONObject(i);
                classBean.setId(item.getString("id"));
                classBean.setVname((i+1)+"."+item.getString("vname"));
                classBean.setAddress(item.getString("address"));
                classItems.add(classBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return classItems;
    }

    public String getQuanXian(){
        String qx = null;
        try {
             qx = new JSONObject(result).getString("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return qx;
    }

    public String getMsg() {
        String msg = null;
        try {
            msg = new JSONObject(result).getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
