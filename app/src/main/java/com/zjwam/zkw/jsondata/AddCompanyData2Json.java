package com.zjwam.zkw.jsondata;

import android.content.Context;

import com.zjwam.zkw.entity.AddCompanyInfo;
import com.zjwam.zkw.util.ZkwPreference;

import org.json.JSONException;
import org.json.JSONObject;
public class AddCompanyData2Json {
    public static JSONObject getCompanyData2Json(AddCompanyInfo addCompanyInfo, Context context){
        JSONObject data = new JSONObject();
        JSONObject company = new JSONObject();

        try {
            company.put("uid", ZkwPreference.getInstance(context).getUid());
            company.put("name",addCompanyInfo.getName());
            company.put("com_type",addCompanyInfo.getCom_type());
            company.put("hangye",addCompanyInfo.getHangye());
            company.put("leibie",addCompanyInfo.getLeibie());
            company.put("legal_person",addCompanyInfo.getLegal_person());
            company.put("phone",addCompanyInfo.getPhone());
            company.put("sch_sheng",addCompanyInfo.getSch_sheng());
            company.put("sch_shi",addCompanyInfo.getSch_shi());
            company.put("sch_qu",addCompanyInfo.getSch_qu());
            company.put("com_address",addCompanyInfo.getCom_address());
            company.put("com_identity",addCompanyInfo.getCom_identity());
            company.put("credit_cade",addCompanyInfo.getCredit_cade());
            data.put("data",company);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
