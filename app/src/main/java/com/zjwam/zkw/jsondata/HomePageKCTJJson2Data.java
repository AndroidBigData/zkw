package com.zjwam.zkw.jsondata;

import android.util.Log;

import com.google.gson.JsonArray;
import com.zjwam.zkw.entity.HomePageKCTJInfo;
import com.zjwam.zkw.entity.HomePageKCTJItemImgs;
import com.zjwam.zkw.entity.HomePageKCTJItemInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomePageKCTJJson2Data {
    private JSONArray result;
    private List<HomePageKCTJInfo> datas;

    public HomePageKCTJJson2Data(JSONArray result) {
        this.result = result;
    }

    public List<HomePageKCTJInfo> getKCTJInfo() throws JSONException {
        datas = new ArrayList<>();
        for (int i = 0 ; i < result.length() ; i++){
            HomePageKCTJInfo homePageKCTJInfo = new HomePageKCTJInfo();
            JSONObject data = result.getJSONObject(i);
            homePageKCTJInfo.setName(data.getString("name"));
            homePageKCTJInfo.setCode(data.getString("code"));
            List<HomePageKCTJItemInfo> info = new ArrayList<>();
            List<HomePageKCTJItemImgs> imgs = new ArrayList<>();
            for (int j = 0 ; j < data.getJSONArray("cate").length();j++){
                HomePageKCTJItemInfo itemInfo = new HomePageKCTJItemInfo();
                JSONObject item = data.getJSONArray("cate").getJSONObject(j);
                itemInfo.setId(item.getString("id"));
                itemInfo.setName(item.getString("name"));
                itemInfo.setWid(item.getString("wid"));
                info.add(itemInfo);
                homePageKCTJInfo.setItemInfos(info);
            }
            for (int j = 0 ; j < data.getJSONArray("app").length();j++){
                HomePageKCTJItemImgs itemInfo = new HomePageKCTJItemImgs();
                JSONObject item = data.getJSONArray("app").getJSONObject(j);
                itemInfo.setId(item.getString("id"));
                itemInfo.setName(item.getString("name"));
                itemInfo.setWid(item.getString("wid"));
                itemInfo.setImg(item.getString("img"));
                imgs.add(itemInfo);
                Log.i("---datas1:",imgs.get(0).getImg());
                homePageKCTJInfo.setItemImgs(imgs);
            }
            datas.add(homePageKCTJInfo);
        }
        Log.i("---datas2:",datas.get(0).getItemImgs().get(0).getImg());
        return datas;
    }
}
