package com.zjwam.zkw.jsondata;

import com.zjwam.zkw.entity.DialogInfo;
import org.json.JSONException;
import org.json.JSONObject;

public class Dialog2Json {

    public static DialogInfo getDialogInfo (String result){
        DialogInfo dialogInfo = null;
        try {
            JSONObject datas = new JSONObject(result);
            dialogInfo = new DialogInfo();
            dialogInfo.setCode(datas.getString("code"));
            dialogInfo.setMsg(datas.getString("msg"));
            JSONObject data = datas.getJSONObject("data");
            dialogInfo.setUid(data.getString("uid"));
            dialogInfo.setType(data.getString("type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dialogInfo;
    }
}
