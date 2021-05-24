package com.txh.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.txh.MyApplication;
import com.txh.R;
import com.txh.json.Message;
import com.txh.json.apply.SubmitApply;
import com.txh.utils.ToastUtil;
import com.txh.utils.UrlUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddApplyActivity extends AppCompatActivity {

    private EditText addApply_productName;
    private EditText addApply_productNum;
    private EditText addApply_name;
    private EditText addApply_type;
    private EditText addApply_count;
    private EditText addApply_price;
    private EditText addApply_detail;
    private EditText addApply_totalMoney;
    private Button addApply_submit;
    private LinearLayout addApply_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apply);

        init();
        addApply_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postSubmitApply();
            }
        });

        addApply_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回功能
                AddApplyActivity.super.onBackPressed();
            }
        });

    }

    private void init() {
        addApply_productName = findViewById(R.id.addApply_productName);
        addApply_productNum = findViewById(R.id.addApply_productNum);
        addApply_name = findViewById(R.id.addApply_name);
        addApply_type = findViewById(R.id.addApply_type);
        addApply_count = findViewById(R.id.addApply_count);
        addApply_price = findViewById(R.id.addApply_price);
        addApply_detail = findViewById(R.id.addApply_detail);
        addApply_totalMoney = findViewById(R.id.addApply_totalMoney);
        addApply_submit = findViewById(R.id.addApply_submit);
        addApply_back = findViewById(R.id.addApply_back);
    }

    /**
     * 向服务器发起post请求，完成提交申请的操作
     */
    private void postSubmitApply() {
        SubmitApply submitApply = new SubmitApply();
        submitApply.setTeacherId(MyApplication.teacherId);
        submitApply.setProductName(addApply_productName.getText().toString());
        submitApply.setProductNum(addApply_productNum.getText().toString());
        submitApply.setName(addApply_name.getText().toString());
        submitApply.setType(addApply_type.getText().toString());
        if (addApply_count.getText().toString().isEmpty()) {
            showMessage("申购数量不能为空");
            return;
        }
        submitApply.setCount(Integer.parseInt(addApply_count.getText().toString()));
        if (addApply_price.getText().toString().isEmpty()) {
            showMessage("参考单价不能为空");
            return;
        }
        submitApply.setPrice(Double.parseDouble(addApply_price.getText().toString()));
        submitApply.setDetail(addApply_detail.getText().toString());
        if (addApply_totalMoney.getText().toString().isEmpty()) {
            showMessage("总金额不能为空");
            return;
        }
        submitApply.setTotalMoney(Double.parseDouble(addApply_totalMoney.getText().toString()));
        Gson gson = new Gson();
        String json = gson.toJson(submitApply);
        //String转请求体
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.Companion.create(json,mediaType);
        //实例化OkHttp的请求器
        OkHttpClient okHttpClient = new OkHttpClient();
        //实例化请求对象
        String url = UrlUtil.url + "/equipment/applyBuy";
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        //把request请求对象传递给okHttpClient请求器，就可以得到一个准备好进行请求的Call对象
        Call call = okHttpClient.newCall(request);
        //异步请求，调用enqueue方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {//请求失败
                showMessage("网络异常！");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {//请求结束，代表跟服务器通信成功，但不一定跟url通信成功，可能404
                if (response.isSuccessful()) {//如果响应是成功的，才进行操作
                    String result = response.body().string();
                    Message message = gson.fromJson(result, Message.class);
                    showMessage(message.getMsg());
                }
            }
        });
    }

    /**
     * Toast出服务器返回的msg
     *
     * @param msg
     */
    private void showMessage(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(AddApplyActivity.this, msg);
            }
        });
    }
}