package com.zjwam.zkw.fragment.VideoPlayer;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lzy.okgo.model.Response;
import com.zjwam.zkw.httputils.VideoPlayerHttp;
import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.IntroduceBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroduceFragment extends Fragment {

private TextView introduce_title,introduce_abstract,introduce_num,introduce_intro;
private RatingBar introduce_rating;
private String id = "";
private Context context;

    public IntroduceFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public IntroduceFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_introduce, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAdded()) {
            id = getArguments().getString("id");
        }
        initView();
        initData();
    }

    private void initData() {
        new VideoPlayerHttp(context).getIntroduceFragment(id);
    }

    private void initView() {
        introduce_title = getActivity().findViewById(R.id.introduce_title);
        introduce_abstract = getActivity().findViewById(R.id.introduce_abstract);
        introduce_num = getActivity().findViewById(R.id.introduce_num);
        introduce_intro = getActivity().findViewById(R.id.introduce_intro);
        introduce_rating = getActivity().findViewById(R.id.introduce_rating);
    }

    public void getIntroduceIfo(Response<IntroduceBean> response) {
        IntroduceBean introduceBean = response.body();
        introduce_title.setText(introduceBean.getName());
        introduce_abstract.setText(introduceBean.getAbstracts());
        introduce_rating.setNumStars(introduceBean.getStar());
        introduce_num.setText(introduceBean.getNum()+"课时    " +  introduceBean.getSnum()+"人已学习，" + introduceBean.getBuynum()+"人已购买");
        introduce_intro.setText(introduceBean.getIntro());
    }
}
