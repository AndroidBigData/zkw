package com.zjwam.zkw.jsondata;

import com.zjwam.zkw.entity.LunboBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LunboJson2Data {
    public List<LunboBean> getLunboData(JSONArray result) throws JSONException {
        List<LunboBean> datas = new ArrayList<>();
        for (int i = 0 ; i < result.length() ; i++ ){
            LunboBean lunboBean = new LunboBean();
            JSONObject data = result.getJSONObject(i);
            lunboBean.setImg(data.getString("img"));
            lunboBean.setClid(data.getInt("clid"));
            datas.add(lunboBean);
        }
        return datas;
    }
}
