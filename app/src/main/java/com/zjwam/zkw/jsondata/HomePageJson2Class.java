package com.zjwam.zkw.jsondata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomePageJson2Class {
    private String result;

    public HomePageJson2Class(String result) {
        this.result = result;
    }

    public JSONArray getLunboData() throws JSONException {
        JSONObject datas = new JSONObject(result);
        JSONArray dada = datas.getJSONArray("banner");
        return dada;
    }
    public JSONArray getKCTJData() throws JSONException {
        JSONObject datas = new JSONObject(result);
        JSONArray dada = datas.getJSONArray("class");
        return dada;
    }
}
