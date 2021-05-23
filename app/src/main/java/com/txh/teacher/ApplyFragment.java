package com.txh.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.txh.R;
import com.txh.json.apply.ApplyDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApplyFragment extends Fragment {

    private View rootView;
    private Context context;

    private ImageView apply_add;
    private RecyclerView apply_list;

    public interface setDetailData {
        void setProductName(String productName);
        void setProductNum(String productNum);
        void setName(String name);
        void setType(String type);
        void setCount(Integer count);
        void setPrice(Double price);
        void setDetail(String detail);
        void setTotalMoney(Double totalMoney);
        void setCreateTime(Date createTime);
        void setBuyStatus(Integer buyStatus);
    }

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
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_apply, container, false);
        }
        initPager();
        return rootView;
    }

    /**
     * 初始化各组件
     */
    private void initPager() {
        apply_add = rootView.findViewById(R.id.apply_add);
        apply_list = rootView.findViewById(R.id.apply_list);
        apply_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddApplyActivity.class));
            }
        });
        //模拟数据
        List<ApplyDetail> applyHistoryList = new ArrayList<>();
        ApplyDetail applyHistory = new ApplyDetail();
        applyHistory.setName("天平");
        applyHistory.setCreateTime(new Date());
        applyHistory.setBuyStatus(0);
        applyHistoryList.add(applyHistory);
        //设置布局管理器
        apply_list.setLayoutManager(new LinearLayoutManager(context));
        //设置Adapter
        ApplyHistoryAdapter applyHistoryAdapter = new ApplyHistoryAdapter(applyHistoryList, context);
        apply_list.setAdapter(applyHistoryAdapter);


//        String productName = applyHistoryList.get(position).getProductName();
//        String productNum = applyHistoryList.get(position).getProductNum();
//        String name = applyHistoryList.get(position).getName();
//        String type = applyHistoryList.get(position).getType();
//        Integer count = applyHistoryList.get(position).getCount();
//        double price = applyHistoryList.get(position).getPrice();
//        String detail = applyHistoryList.get(position).getDetail();
//        double totalMoney = applyHistoryList.get(position).getTotalMoney();
//        Date createTime = applyHistoryList.get(position).getCreateTime();
    }
}