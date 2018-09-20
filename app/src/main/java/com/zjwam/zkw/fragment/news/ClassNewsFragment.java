package com.zjwam.zkw.fragment.news;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjwam.zkw.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassNewsFragment extends Fragment {
    private Context context;

    public ClassNewsFragment() {
        // Required empty public constructor
    }

    public static ClassNewsFragment newInstance(Context context) {
        ClassNewsFragment fragment = new ClassNewsFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_news, container, false);
    }

}
