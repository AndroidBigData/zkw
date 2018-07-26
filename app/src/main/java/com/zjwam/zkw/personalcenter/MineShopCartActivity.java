package com.zjwam.zkw.personalcenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zjwam.zkw.BaseActivity;
import com.zjwam.zkw.HttpUtils.HttpErrorMsg;
import com.zjwam.zkw.HttpUtils.PersonalCenterHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.PersonalMineShopCardAdapter;
import com.zjwam.zkw.callback.JsonCallback;
import com.zjwam.zkw.entity.EmptyBean;
import com.zjwam.zkw.entity.MineShopCartBean;
import com.zjwam.zkw.entity.PayMsgBean;
import com.zjwam.zkw.entity.ResponseBean;
import com.zjwam.zkw.entity.SimpleResponse;
import com.zjwam.zkw.pay.PayPreviewActivity;
import com.zjwam.zkw.pay.PayResult;
import com.zjwam.zkw.util.Config;
import com.zjwam.zkw.util.MyException;
import com.zjwam.zkw.util.ZkwPreference;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MineShopCartActivity extends BaseActivity {

    private ListView shopcard_recyclerview;
    private ImageView mine_shopcard_back, shopcard_nodata;
    private TextView mine_shopcard_del, mine_shopcard_checkout, mine_shopcard_price;
    private CheckBox mine_shopcard_checkall;
    private PersonalMineShopCardAdapter mineShopCardAdapter;
    private String uid, id = "";
    private List<MineShopCartBean.getShopCartItems> list;
    private double price = 0;
    private static final int SDK_PAY_FLAG = 1;
    private PersonalCenterHttp personalCenterHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_shop_cart);
        initView();
        initData();
    }

    private void initData() {
        uid = ZkwPreference.getInstance(getBaseContext()).getUid();
        personalCenterHttp = new PersonalCenterHttp(this);
        shopcard_recyclerview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO Auto-generated method stub
                // 取得ViewHolder对象
                PersonalMineShopCardAdapter.ViewHolder viewHolder = (PersonalMineShopCardAdapter.ViewHolder) view.getTag();
                // 改变CheckBox的状态
                viewHolder.shopcard_checkbok.toggle();
                // 将CheckBox的选中状况记录下来
                list.get(i).setChecked(viewHolder.shopcard_checkbok.isChecked());
                // 调整选定条目
                if (viewHolder.shopcard_checkbok.isChecked() == true) {
                    price += Integer.parseInt(list.get(i).getPrice());
                } else {
                    price -= Integer.parseInt(list.get(i).getPrice());
                }
                if (mine_shopcard_checkall.isChecked()) {
                    mine_shopcard_checkall.setChecked(false);
                } else {
                    int item = 0;
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).isChecked()) {
                            item = item + 1;
                        }
                    }
                    if (item == list.size()) {
                        mine_shopcard_checkall.setChecked(true);
                    }
                }
                mine_shopcard_price.setText(String.valueOf(price));
            }
        });
        mine_shopcard_back.setOnClickListener(onClickListener);
        mine_shopcard_del.setOnClickListener(onClickListener);
        mine_shopcard_checkout.setOnClickListener(onClickListener);
        mine_shopcard_checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                price = 0.0;
                if (mine_shopcard_checkall.isChecked()) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChecked(true);
                        price += Double.parseDouble(list.get(i).getPrice());
                    }
                    mine_shopcard_price.setText(String.valueOf(price));
                    mineShopCardAdapter.notifyDataSetChanged();
                    mine_shopcard_checkall.setChecked(true);
                } else {
                    price = 0.0;
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChecked(false);
                        if (list.get(i).isChecked()) {
                            price += Double.parseDouble(list.get(i).getPrice());
                        }
                    }
                    mine_shopcard_price.setText(String.valueOf(price));
                    mineShopCardAdapter.notifyDataSetChanged();
                    mine_shopcard_checkall.setChecked(false);
                }
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mine_shopcard_back:
                    finish();
                    break;
                case R.id.mine_shopcard_del:
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        // 得到对应集合元素
                        MineShopCartBean.getShopCartItems deleteItem = (MineShopCartBean.getShopCartItems) it.next();
                        // 判断
                        if (deleteItem.isChecked()) {
                            // 从集合中删除上一次next方法返回的元素
//                            it.remove();
                            id = id + String.valueOf(deleteItem.getId()) + "_";
                        }
                    }
                    personalCenterHttp.deleteShopCard(uid,id);
                    break;
                case R.id.mine_shopcard_checkout:
                    id = "";
                    Iterator its = list.iterator();
                    while (its.hasNext()) {
                        MineShopCartBean.getShopCartItems checkedItem = (MineShopCartBean.getShopCartItems) its.next();
                        if (checkedItem.isChecked()) {
                            id = id + String.valueOf(checkedItem.getId()) + "_";
                        }
                    }
                    if (id.length() > 1) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", id);
                        Intent intent = new Intent(getBaseContext(), PayPreviewActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "请选择", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public void deleteShopCard(Response<ResponseBean<EmptyBean>> response){
        ResponseBean<EmptyBean> data = response.body();
        Toast.makeText(getBaseContext(), data.msg, Toast.LENGTH_SHORT).show();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            // 得到对应集合元素
            MineShopCartBean.getShopCartItems deleteItem = (MineShopCartBean.getShopCartItems) it.next();
            // 判断
            if (deleteItem.isChecked()) {
                // 从集合中删除上一次next方法返回的元素
                it.remove();
            }
        }
        mineShopCardAdapter.notifyDataSetChanged();
        price = 0.0;
        mine_shopcard_price.setText(String.valueOf(price));
        if (list.size() > 0) {
            shopcard_nodata.setVisibility(View.GONE);
        } else {
            shopcard_nodata.setVisibility(View.VISIBLE);
        }
    }
    public void deleteShopCardError(Response<ResponseBean<EmptyBean>> response){
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        error(error);
    }

    public void getMineShopCard(Response<ResponseBean<MineShopCartBean>> response) {
        mine_shopcard_checkall.setChecked(false);
        mine_shopcard_price.setText("0.0");
        ResponseBean<MineShopCartBean> dada = response.body();
        list = dada.data.getCar();
        mineShopCardAdapter = new PersonalMineShopCardAdapter(getBaseContext(), list);
        shopcard_recyclerview.setAdapter(mineShopCardAdapter);
        if (list.size() > 0) {
            shopcard_nodata.setVisibility(View.GONE);
        } else {
            shopcard_nodata.setVisibility(View.VISIBLE);
        }
    }

    public void getMineShopCardError(Response<ResponseBean<MineShopCartBean>> response) {
        Throwable exception = response.getException();
        String error = HttpErrorMsg.getErrorMsg(exception);
        error(error);
    }

    private void initView() {
        shopcard_recyclerview = findViewById(R.id.shopcard_recyclerview);
        mine_shopcard_back = findViewById(R.id.mine_shopcard_back);
        mine_shopcard_del = findViewById(R.id.mine_shopcard_del);
        mine_shopcard_checkout = findViewById(R.id.mine_shopcard_checkout);
        mine_shopcard_price = findViewById(R.id.mine_shopcard_price);
        mine_shopcard_checkall = findViewById(R.id.mine_shopcard_checkall);
        shopcard_nodata = findViewById(R.id.shopcard_nodata);
    }

    @Override
    public void onResume() {
        super.onResume();
        personalCenterHttp.getMineShopCard(uid);
    }
}
