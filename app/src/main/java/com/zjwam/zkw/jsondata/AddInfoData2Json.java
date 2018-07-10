package com.zjwam.zkw.jsondata;

import android.content.Context;

import com.zjwam.zkw.entity.AddInfo;
import com.zjwam.zkw.util.ZkwPreference;

import org.json.JSONException;
import org.json.JSONObject;

public class AddInfoData2Json {
    public JSONObject addInfoData2Json(AddInfo addInfo, Context context) {

        JSONObject data = new JSONObject();
        JSONObject student = new JSONObject();

        try {
            student.put("uid", ZkwPreference.getInstance(context).getUid());
            student.put("name",addInfo.getName());
            student.put("sex",addInfo.getSex());
            student.put("phone",addInfo.getPhone());
            student.put("num",addInfo.getNum());
            student.put("address",addInfo.getAdress());
            student.put("wx",addInfo.getWx());
            student.put("qq",addInfo.getQq());
            student.put("school_name",addInfo.getSchool_name());
            student.put("province",addInfo.getSchool_adress_province());
            student.put("city",addInfo.getSchool_adress_city());
            student.put("area",addInfo.getSchool_adress_area());
            student.put("address_more",addInfo.getSchool_adress_more());
            student.put("xueli",addInfo.getSchool_type());
            student.put("grade",addInfo.getGrade());
//            student.put("kemu",addInfo.getKemu());
            student.put("subject",addInfo.getSubject());
            student.put("school_type",addInfo.getSchool_xz());
            student.put("subject_more",addInfo.getSubjec_more());
            student.put("max_xl",addInfo.getMax_xl());
            student.put("graduation",addInfo.getGraduation());
            student.put("graduation_address",addInfo.getGraduation_address());
            data.put("data",student);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
