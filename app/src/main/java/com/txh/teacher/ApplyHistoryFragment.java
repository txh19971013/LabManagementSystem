package com.txh.teacher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.txh.R;
import com.txh.json.apply.ApplyHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApplyHistoryFragment extends Fragment {

    private View rootView;

    private RecyclerView apply_list;

    public ApplyHistoryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ApplyHistoryFragment newInstance() {
        ApplyHistoryFragment fragment = new ApplyHistoryFragment();
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
            rootView = inflater.inflate(R.layout.fragment_apply_history, container, false);
        }
        apply_list = rootView.findViewById(R.id.apply_list);
        List<ApplyHistory> applyHistoryList = new ArrayList<>();
        ApplyHistory applyHistory = new ApplyHistory();
        applyHistory.setName("天平");
        applyHistory.setCreateTime(new Date());
        applyHistory.setBuyStatus(0);
        applyHistoryList.add(applyHistory);
        ApplyHistoryAdapter applyHistoryAdapter = new ApplyHistoryAdapter(applyHistoryList);
        apply_list.setAdapter(applyHistoryAdapter);
        return rootView;
    }
}