package com.txh.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.txh.R;
import com.txh.json.apply.ApplyHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApplyHistoryActivity extends AppCompatActivity {
    private RecyclerView apply_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_history);

        apply_list = findViewById(R.id.apply_list);
        //模拟数据
        List<ApplyHistory> applyHistoryList = new ArrayList<>();
        ApplyHistory applyHistory = new ApplyHistory();
        applyHistory.setName("天平");
        applyHistory.setCreateTime(new Date());
        applyHistory.setBuyStatus(0);
        applyHistoryList.add(applyHistory);
        ApplyHistoryAdapter applyHistoryAdapter = new ApplyHistoryAdapter(applyHistoryList);
        apply_list.setAdapter(applyHistoryAdapter);
    }
}