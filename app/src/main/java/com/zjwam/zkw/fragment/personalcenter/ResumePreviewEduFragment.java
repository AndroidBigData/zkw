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
import com.zjwam.zkw.entity.ResumePreviewEduBean;
import com.zjwam.zkw.mvp.presenter.ResumePreviewEduPresenter;
import com.zjwam.zkw.mvp.presenter.ipresenter.IResumePreviewPresenter;
import com.zjwam.zkw.mvp.view.IResumePreviewEduView;
import com.zjwam.zkw.personalcenter.job.ResumePreviewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumePreviewEduFragment extends Fragment implements IResumePreviewEduView{

    private TextView preview_edu_school,preview_edu_major,preview_edu_num,preview_edu_enrolment,preview_edu_graduation;
    private Context context;
    private String id;
    private IResumePreviewPresenter previewPresenter;
    public ResumePreviewEduFragment() {
        // Required empty public constructor
    }

    public static ResumePreviewEduFragment newInstance(Context context,String id) {
        Bundle bundle = new Bundle();
        ResumePreviewEduFragment fragment = new ResumePreviewEduFragment();
        bundle.putString("id",id);
        fragment.context = context;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.resume_preview_edu, container, false);
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
        previewPresenter = new ResumePreviewEduPresenter(context,this);
        previewPresenter.getPreviewData(id);
    }

    private void initView() {
        preview_edu_school = getActivity().findViewById(R.id.preview_edu_school);
        preview_edu_major = getActivity().findViewById(R.id.preview_edu_major);
        preview_edu_num = getActivity().findViewById(R.id.preview_edu_num);
        preview_edu_enrolment = getActivity().findViewById(R.id.preview_edu_enrolment);
        preview_edu_graduation = getActivity().findViewById(R.id.preview_edu_graduation);
    }

    @Override
    public void showMsg(String msg) {
        if (context instanceof ResumePreviewActivity){
            ((ResumePreviewActivity) context).error(msg);
        }
    }

    @Override
    public void setPreview(ResumePreviewEduBean previewEduBean) {
        preview_edu_school.setText(previewEduBean.getSchool());
        preview_edu_major.setText(previewEduBean.getProfessional());
        preview_edu_num.setText(previewEduBean.getSchool_nature());
        preview_edu_enrolment.setText(previewEduBean.getBegin_time());
        preview_edu_graduation.setText(previewEduBean.getEnd_time());
    }
}
