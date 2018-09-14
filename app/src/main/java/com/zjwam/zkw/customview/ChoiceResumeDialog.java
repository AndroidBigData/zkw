package com.zjwam.zkw.customview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zjwam.zkw.R;
import com.zjwam.zkw.adapter.ChoiceResumeAdapter;
import com.zjwam.zkw.entity.MineJobResumeBean;

import java.util.List;

public class ChoiceResumeDialog extends Dialog {
    private Context context;
    private LRecyclerView choice_resume_item;
    private ChoiceResumeAdapter resumeAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<MineJobResumeBean> data;
    private SendResume sendResume;
    public ChoiceResumeDialog(@NonNull Context context, List<MineJobResumeBean> data) {
        super(context, R.style.dialog);
        this.context = context;
        this.data = data;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.choice_resume_dialog,null,false);
        setContentView(view);
        choice_resume_item = view.findViewById(R.id.choice_resume_item);
        resumeAdapter = new ChoiceResumeAdapter(context);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(resumeAdapter);
        choice_resume_item.setAdapter(lRecyclerViewAdapter);
        choice_resume_item.setLayoutManager(new LinearLayoutManager(context));
        resumeAdapter.addAll(data);
        choice_resume_item.setPullRefreshEnabled(false);
        choice_resume_item.setLoadMoreEnabled(false);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                sendResume.getId(String.valueOf(resumeAdapter.getDataList().get(position).getId()));
            }
        });
    }

    public void setSendResume(SendResume sendResume) {
        this.sendResume = sendResume;
    }

    public interface SendResume{
        void getId(String rid);
    }
}
