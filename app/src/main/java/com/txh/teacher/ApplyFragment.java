package com.txh.teacher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.txh.R;

public class ApplyFragment extends Fragment {

    private View rootView;

    private Button apply_show, apply_apply;
    private TextView apply_equipmentName;

    public ApplyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ApplyFragment newInstance() {
        ApplyFragment fragment = new ApplyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_apply, container, false);
        }
        initPager();
        apply_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.vp2_teacher, ApplyHistoryFragment.newInstance()).commitAllowingStateLoss();
            }
        });
        return rootView;
    }

    /**
     * 初始化各组件
     */
    private void initPager() {
        apply_show = rootView.findViewById(R.id.apply_show);
        apply_apply = rootView.findViewById(R.id.apply_apply);
        apply_equipmentName = rootView.findViewById(R.id.apply_equipmentName);
    }
}