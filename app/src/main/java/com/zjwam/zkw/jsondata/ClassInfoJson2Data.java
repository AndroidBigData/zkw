package com.zjwam.zkw.jsondata;

import com.zjwam.zkw.entity.ClassInfo;
import com.zjwam.zkw.entity.ClassTypeInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClassInfoJson2Data {
    private List<ClassInfo> data;
    public List<ClassInfo> getClassItem (String result) throws JSONException {
        data = new ArrayList<>();
        JSONArray classArr = new JSONObject(result).getJSONArray("class_list");
        for (int i = 0 ; i < classArr.length() ; i++){
            JSONObject classitem =  classArr.getJSONObject(i);
            ClassInfo classInfo = new ClassInfo();
            classInfo.setImg(classitem.getString("img"));
            classInfo.setType(classitem.getString("type"));
            classInfo.setName(classitem.getString("name"));
            classInfo.setAbstracts(classitem.getString("abstracts"));
            classInfo.setStar(classitem.getInt("star"));
            classInfo.setNum(classitem.getInt("num"));
            classInfo.setSnum(classitem.getInt("snum"));
            classInfo.setId(classitem.getInt("id"));
            data.add(classInfo);
        }
        return data;
    }

    public List<ClassTypeInfo> getClassTypeItem (String result) throws JSONException {
        List<ClassTypeInfo> data = new ArrayList<>();
        JSONArray typeItem = new JSONObject(result).getJSONArray("web");
        for (int i=0;i<typeItem.length();i++){
            JSONObject type = typeItem.getJSONObject(i);
            ClassTypeInfo classTypeInfo = new ClassTypeInfo();
            classTypeInfo.setId(type.getString("id"));
            classTypeInfo.setName(type.getString("name"));
            data.add(classTypeInfo);
        }
        return data;
    }

}
