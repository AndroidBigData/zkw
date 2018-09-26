package com.zjwam.zkw.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.TestQueryResultDialogAdapter;
import com.zjwam.zkw.entity.TestQueryResultDialogBean;

import java.util.List;

public class TestQueryResultDialog extends Dialog {
    private Context context;
    private List<TestQueryResultDialogBean> data;
    private TestQueryResultDialogAdapter testQueryResultDialogAdapter;
    private RecyclerView query_result_recyclerview;
    private TextView IKnow;

    public TestQueryResultDialog(@NonNull Context context, List<TestQueryResultDialogBean> data) {
        super(context, R.style.dialog);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.test_query_result_dialog,null,false);
        setContentView(view);
        query_result_recyclerview = view.findViewById(R.id.query_result_recyclerview);
        IKnow = view.findViewById(R.id.IKnow);
        testQueryResultDialogAdapter = new TestQueryResultDialogAdapter(context);
        testQueryResultDialogAdapter.addAll(data);
        query_result_recyclerview.setAdapter(testQueryResultDialogAdapter);
        query_result_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        testQueryResultDialogAdapter.setOnClickListener(new TestQueryResultDialogAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.i("position:",""+position);
            }
        });
        IKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
