package com.txh.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txh.R;

public class EquipmentActivity extends AppCompatActivity {

    private TextView equipment_name;
    private TextView equipment_type;
    private TextView equipment_count;
    private TextView equipment_buyDate;
    private TextView equipment_createTime;
    private TextView equipment_updateTime;
    private LinearLayout equipment_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);

        init();
        //接收Intent和Bundle传过来的数据并渲染界面
        Bundle bundle = getIntent().getExtras();
        equipment_name.setText(bundle.getString("name"));
        equipment_type.setText(bundle.getString("type"));
        equipment_count.setText(bundle.getString("count"));
        equipment_buyDate.setText(bundle.getString("buyDate"));
        equipment_createTime.setText(bundle.getString("createTime"));
        equipment_updateTime.setText(bundle.getString("updateTime"));

        //返回的点击事件
        equipment_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EquipmentActivity.super.onBackPressed();
            }
        });
    }

    private void init() {
        equipment_name = findViewById(R.id.equipment_name);
        equipment_type = findViewById(R.id.equipment_type);
        equipment_count = findViewById(R.id.equipment_count);
        equipment_buyDate = findViewById(R.id.equipment_buyDate);
        equipment_createTime = findViewById(R.id.equipment_createTime);
        equipment_updateTime = findViewById(R.id.equipment_updateTime);
        equipment_back = findViewById(R.id.equipment_back);
    }
}