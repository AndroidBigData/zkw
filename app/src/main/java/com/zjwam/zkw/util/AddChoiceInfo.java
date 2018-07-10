package com.zjwam.zkw.util;

import com.zjwam.zkw.entity.JsonBean;
import java.util.ArrayList;
import java.util.List;

public class AddChoiceInfo {
    //性别
    private List<String> optionsSex;
    //学历-年级
    private List<String> optionsxueli;
    private List<List<String>> optionsGrade;

    //学校类型
    private List<String> schoolType;

    //科目
    private List<String> teacherSubjecdt;

    //企业类型
    private List<String> companyType;
    //公司行业 1级
    private List<String> companyIndustry;
    //具体行业
    private List<List<String>> companyIndustry_item;
    //合作身份
    private List<String> identity;

    public List<String> addSex(){
        optionsSex = new ArrayList<>();
        optionsSex.add("男");
        optionsSex.add("女");
        return optionsSex;
    }
    public  List<String> addXueli(){
        optionsxueli = new ArrayList<>();
        optionsxueli.add("幼儿园");
        optionsxueli.add("小学");
        optionsxueli.add("初中");
        optionsxueli.add("高中");
        optionsxueli.add("中专");
        optionsxueli.add("专科");
        optionsxueli.add("本科");
        optionsxueli.add("研究生");
        optionsxueli.add("博士");
        return optionsxueli;
    }

    //学生教师公用  0表示学生信息完善，其他任意数字表示老师
    public List<List<String>> addGrade(int type){
        ArrayList<String> options2Items_01 = new ArrayList<>();
        options2Items_01.add("小班");
        options2Items_01.add("中班");
        options2Items_01.add("大班");
        if (type == 0){
            options2Items_01.add("已毕业");
        }
        ArrayList<String> options2Items_02 = new ArrayList<>();
        options2Items_02.add("一年级");
        options2Items_02.add("二年级");
        options2Items_02.add("三年级");
        options2Items_02.add("四年级");
        options2Items_02.add("五年级");
        options2Items_02.add("六年级");
        if (type == 0){
            options2Items_02.add("已毕业");
        }

        ArrayList<String> options2Items_07 = new ArrayList<>();
        options2Items_07.add("七年级");
        options2Items_07.add("八年级");
        options2Items_07.add("九年级");
        if (type == 0){
            options2Items_07.add("已毕业");
        }

        ArrayList<String> options2Items_03 = new ArrayList<>();
        options2Items_03.add("高一");
        options2Items_03.add("高二");
        options2Items_03.add("高三");
        if (type == 0){
            options2Items_03.add("已毕业");
        }

        ArrayList<String> options2Items_04 = new ArrayList<>();
        options2Items_04.add("中专一");
        options2Items_04.add("中专二");
        options2Items_04.add("中专三");
        if (type == 0){
            options2Items_04.add("已毕业");
        }

        ArrayList<String> options2Items_08 = new ArrayList<>();
        options2Items_08.add("专一");
        options2Items_08.add("专二");
        options2Items_08.add("专三");
        if (type == 0){
            options2Items_08.add("已毕业");
        }

        ArrayList<String> options2Items_05 = new ArrayList<>();
        options2Items_05.add("本一");
        options2Items_05.add("本二");
        options2Items_05.add("本三");
        options2Items_05.add("本四");
        if (type == 0){
            options2Items_05.add("已毕业");
        }

        ArrayList<String> options2Items_06 = new ArrayList<>();
        options2Items_06.add("研一");
        options2Items_06.add("研二");
        options2Items_06.add("研三");
        if (type == 0){
            options2Items_06.add("已毕业");
        }

        ArrayList<String> options2Items_09 = new ArrayList<>();
        options2Items_09.add("博士一");
        options2Items_09.add("博士二");
        options2Items_09.add("博士三");
        options2Items_09.add("博士四");
        if (type == 0){
            options2Items_09.add("已毕业");
        }

        optionsGrade = new ArrayList<>();
        optionsGrade.add(options2Items_01);
        optionsGrade.add(options2Items_02);
        optionsGrade.add(options2Items_07);
        optionsGrade.add(options2Items_03);
        optionsGrade.add(options2Items_04);
        optionsGrade.add(options2Items_08);
        optionsGrade.add(options2Items_05);
        optionsGrade.add(options2Items_06);
        optionsGrade.add(options2Items_09);
        return optionsGrade;
    }
    public List<String> getChoolType(){
        schoolType = new ArrayList<>();
        schoolType.add("公办");
        schoolType.add("民办");
        return schoolType;
    }

    public List<String> getTeacherSubjecdt() {
        teacherSubjecdt = new ArrayList<>();
        teacherSubjecdt.add("语文");
        teacherSubjecdt.add("数学");
        teacherSubjecdt.add("英语");
        teacherSubjecdt.add("物理");
        teacherSubjecdt.add("化学");
        teacherSubjecdt.add("生物");
        teacherSubjecdt.add("政治");
        teacherSubjecdt.add("历史");
        teacherSubjecdt.add("地理");
        teacherSubjecdt.add("其他");
        return teacherSubjecdt;
    }

    public List<String> getCompanyType() {
        companyType = new ArrayList<>();
        companyType.add("国防机构");
        companyType.add("企业");
        companyType.add("事业单位");
        companyType.add("政府机关");
        companyType.add("社会团体");
        companyType.add("民办非企业单位");
        companyType.add("基金会");
        companyType.add("其他");
        return companyType;
    }

    public List<String> getCompanyIndustry() {
        companyIndustry = new ArrayList<>();
        companyIndustry.add("教育/培训");
        companyIndustry.add("政府/非盈利机构");
        companyIndustry.add("IT/互联网");
        companyIndustry.add("消费品");
        companyIndustry.add("能源/环保");
        companyIndustry.add("机械制造");
        companyIndustry.add("其他行业");
        return companyIndustry;
    }

    public List<List<String>> getCompanyIndustry_item() {
        ArrayList<String> options2Items_01 = new ArrayList<>();
        options2Items_01.add("院校");
        options2Items_01.add("IT培训");
        options2Items_01.add("小初高培训");
        options2Items_01.add("职业资格证培训");
        options2Items_01.add("出国留学");
        options2Items_01.add("其他");
        ArrayList<String> options2Items_02 = new ArrayList<>();
        options2Items_02.add("政府/机关");
        options2Items_02.add("多元化业务集团公司");
        options2Items_02.add("农/林/牧/渔");
        options2Items_02.add("其他");
        ArrayList<String> options2Items_03 = new ArrayList<>();
        options2Items_03.add("移动互联网");
        options2Items_03.add("电子商务");
        options2Items_03.add("企业服务");
        options2Items_03.add("教育");
        options2Items_03.add("O2O");
        options2Items_03.add("文化娱乐");
        options2Items_03.add("游戏");
        options2Items_03.add("医疗健康");
        options2Items_03.add("生活服务");
        options2Items_03.add("旅游");
        options2Items_03.add("计算机软件");
        options2Items_03.add("医疗健康");
        options2Items_03.add("其他");
        ArrayList<String> options2Items_04 = new ArrayList<>();
        options2Items_04.add("食品/饮料/日化/烟酒");
        options2Items_04.add("服装/纺织/家具/家电");
        options2Items_04.add("珠宝首饰/工艺品");
        options2Items_04.add("奢饰品/收藏品");
        options2Items_04.add("办公用品");
        options2Items_04.add("其他");
        ArrayList<String> options2Items_05 = new ArrayList<>();
        options2Items_05.add("石油/化工");
        options2Items_05.add("矿产/地产");
        options2Items_05.add("挖掘/冶炼");
        options2Items_05.add("电利/水利");
        options2Items_05.add("环保");
        options2Items_05.add("新能源");
        options2Items_05.add("其他");
        ArrayList<String> options2Items_06 = new ArrayList<>();
        options2Items_06.add("印刷/包装/造纸");
        options2Items_06.add("机械重工");
        options2Items_06.add("加工模具");
        options2Items_06.add("汽车维修/美容");
        options2Items_06.add("其他");
        ArrayList<String> options2Items_07 = new ArrayList<>();
        options2Items_07.add("其他");
        companyIndustry_item = new ArrayList<>();
        companyIndustry_item.add(options2Items_01);
        companyIndustry_item.add(options2Items_02);
        companyIndustry_item.add(options2Items_03);
        companyIndustry_item.add(options2Items_04);
        companyIndustry_item.add(options2Items_05);
        companyIndustry_item.add(options2Items_06);
        companyIndustry_item.add(options2Items_07);
        return companyIndustry_item;
    }

    public List<String> getIdentity() {
        identity = new ArrayList<>();
        identity.add("学校");
        identity.add("教育机构");
        identity.add("企业");
        identity.add("政府机构");
        return identity;
    }
}
