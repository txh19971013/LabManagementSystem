package com.txh.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.txh.R;

import java.util.Date;

public class ApplyDetailActivity extends AppCompatActivity{

    private TextView applyDetail_productName;
    private TextView applyDetail_productNum;
    private TextView applyDetail_name;
    private TextView applyDetail_type;
    private TextView applyDetail_count;
    private TextView applyDetail_price;
    private TextView applyDetail_detail;
    private TextView applyDetail_totalMoney;
    private TextView applyDetail_createTime;
    private TextView applyDetail_buyStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_detail);

        init();
        //接收Intent和Bundle传过来的数据并渲染界面
        Bundle bundle = getIntent().getExtras();
        applyDetail_productName.setText(bundle.getString("productName"));
        applyDetail_productNum.setText(bundle.getString("productNum"));
        applyDetail_name.setText(bundle.getString("name"));
        applyDetail_type.setText(bundle.getString("type"));
        applyDetail_count.setText(bundle.getString("count"));
        applyDetail_price.setText(bundle.getString("price"));
        applyDetail_detail.setText(bundle.getString("detail"));
        applyDetail_totalMoney.setText(bundle.getString("totalMoney"));
        applyDetail_createTime.setText(bundle.getString("createTime"));
        Integer buyStatus = bundle.getInt("buyStatus");
        switch (buyStatus) {
            case 0:
                applyDetail_buyStatus.setText("审核中……");
                applyDetail_buyStatus.setBackgroundResource(R.drawable.shape_reviewing_corner_10dp);
                break;
            case 1:
                applyDetail_buyStatus.setText("已通过");
                applyDetail_buyStatus.setBackgroundResource(R.drawable.shape_accept_corner_10dp);
                break;
            case 2:
                applyDetail_buyStatus.setText("已拒绝");
                applyDetail_buyStatus.setBackgroundResource(R.drawable.shape_refuse_corner_10dp);
                break;
        }

    }

    private void init() {
        applyDetail_productName = findViewById(R.id.applyDetail_productName);
        applyDetail_productNum = findViewById(R.id.applyDetail_productNum);
        applyDetail_name = findViewById(R.id.applyDetail_name);
        applyDetail_type = findViewById(R.id.applyDetail_type);
        applyDetail_count = findViewById(R.id.applyDetail_count);
        applyDetail_price = findViewById(R.id.applyDetail_price);
        applyDetail_detail = findViewById(R.id.applyDetail_detail);
        applyDetail_totalMoney = findViewById(R.id.applyDetail_totalMoney);
        applyDetail_createTime = findViewById(R.id.applyDetail_createTime);
        applyDetail_buyStatus = findViewById(R.id.applyDetail_buyStatus);
    }
}