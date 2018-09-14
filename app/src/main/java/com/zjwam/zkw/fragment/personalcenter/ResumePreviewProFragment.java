package com.zjwam.zkw.fragment.personalcenter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.ResumePreviewProBean;
import com.zjwam.zkw.mvp.presenter.ResumePreviewProPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IResumePreviewPresenter;
import com.zjwam.zkw.mvp.view.IResumePreviewProView;
import com.zjwam.zkw.personalcenter.job.ResumePreviewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumePreviewProFragment extends Fragment implements IResumePreviewProView{

    private Context context;
    private TextView preview_pro_name,preview_pro_begin,preview_pro_end,preview_pro_describe,preview_pro_duty;
    private String id;
    private IResumePreviewPresenter previewPresenter;
    public ResumePreviewProFragment() {
        // Required empty public constructor
    }

    public static ResumePreviewProFragment newInstance(Context context,String id) {

        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        ResumePreviewProFragment fragment = new ResumePreviewProFragment();
        fragment.context = context;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.resume_preview_pro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            id = bundle.getString("id");
        }
        initView();
        initData();
    }

    private void initData() {
        previewPresenter = new ResumePreviewProPresenter(context,this);
        previewPresenter.getPreviewData(id);
    }

    private void initView() {
        preview_pro_name = getActivity().findViewById(R.id.preview_pro_name);
        preview_pro_begin = getActivity().findViewById(R.id.preview_pro_begin);
        preview_pro_end = getActivity().findViewById(R.id.preview_pro_end);
        preview_pro_describe = getActivity().findViewById(R.id.preview_pro_describe);
        preview_pro_duty = getActivity().findViewById(R.id.preview_pro_duty);
    }

    @Override
    public void showMsg(String msg) {
        if (context instanceof ResumePreviewActivity){
            ((ResumePreviewActivity) context).error(msg);
        }
    }

    @Override
    public void setPreview(ResumePreviewProBean data) {
        preview_pro_name.setText(data.getProject_name());
        preview_pro_begin.setText(data.getBegin_time());
        preview_pro_end.setText(data.getEnd_time());
        preview_pro_describe.setText(data.getIntro());
        preview_pro_duty.setText(data.getResponsibility());
    }
}
