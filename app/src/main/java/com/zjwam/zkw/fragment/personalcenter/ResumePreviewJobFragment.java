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
import com.zjwam.zkw.entity.ResumePreviewJobBean;
import com.zjwam.zkw.mvp.presenter.ResumePreviewJobPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IResumePreviewPresenter;
import com.zjwam.zkw.mvp.view.IResumePreviewJobView;
import com.zjwam.zkw.personalcenter.job.ResumePreviewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumePreviewJobFragment extends Fragment implements IResumePreviewJobView{

    private Context context;
    private TextView preview_job_company,preview_job_begin,preview_job_end,preview_job_type,preview_job_name,preview_job_money,preview_job_describe;
    private String id;
    private IResumePreviewPresenter previewPresenter;
    public ResumePreviewJobFragment() {
        // Required empty public constructor
    }

    public static ResumePreviewJobFragment newInstance(Context context,String id) {
        Bundle bundle = new Bundle();
        ResumePreviewJobFragment fragment = new ResumePreviewJobFragment();
        bundle.putString("id",id);
        fragment.context = context;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.resume_preview_job, container, false);
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
        previewPresenter = new ResumePreviewJobPresenter(context,this);
        previewPresenter.getPreviewData(id);
    }

    private void initView() {
        preview_job_company = getActivity().findViewById(R.id.preview_job_company);
        preview_job_begin = getActivity().findViewById(R.id.preview_job_begin);
        preview_job_end = getActivity().findViewById(R.id.preview_job_end);
        preview_job_type = getActivity().findViewById(R.id.preview_job_type);
        preview_job_name = getActivity().findViewById(R.id.preview_job_name);
        preview_job_money = getActivity().findViewById(R.id.preview_job_money);
        preview_job_describe = getActivity().findViewById(R.id.preview_job_describe);
    }

    @Override
    public void showMsg(String msg) {
        if (context instanceof ResumePreviewActivity){
            ((ResumePreviewActivity) context).error(msg);
        }
    }

    @Override
    public void setPreview(ResumePreviewJobBean data) {
        preview_job_company.setText(data.getWork_company());
        preview_job_begin.setText(data.getBegin_time());
        preview_job_end.setText(data.getEnd_time());
        preview_job_type.setText(data.getWork_company_nature());
        preview_job_name.setText(data.getWork_job());
        preview_job_money.setText(String.valueOf(data.getWork_emolument_high()));
        preview_job_describe.setText(data.getResponsibility());
    }
}
