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
                //θΏεεθ½
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
     * εζε‘ε¨εθ΅·postθ―·ζ±οΌε?ζζδΊ€η³θ―·ηζδ½
     */
    private void postSubmitApply() {
        SubmitApply submitApply = new SubmitApply();
        submitApply.setTeacherId(MyApplication.teacherId);
        submitApply.setProductName(addApply_productName.getText().toString());
        submitApply.setProductNum(addApply_productNum.getText().toString());
        submitApply.setName(addApply_name.getText().toString());
        submitApply.setType(addApply_type.getText().toString());
        if (addApply_count.getText().toString().isEmpty()) {
            showMessage("η³θ΄­ζ°ιδΈθ½δΈΊη©Ί");
            return;
        }
        submitApply.setCount(Integer.parseInt(addApply_count.getText().toString()));
        if (addApply_price.getText().toString().isEmpty()) {
            showMessage("εθεδ»·δΈθ½δΈΊη©Ί");
            return;
        }
        submitApply.setPrice(Double.parseDouble(addApply_price.getText().toString()));
        submitApply.setDetail(addApply_detail.getText().toString());
        if (addApply_totalMoney.getText().toString().isEmpty()) {
            showMessage("ζ»ιι’δΈθ½δΈΊη©Ί");
            return;
        }
        submitApply.setTotalMoney(Double.parseDouble(addApply_totalMoney.getText().toString()));
        Gson gson = new Gson();
        String json = gson.toJson(submitApply);
        //Stringθ½¬θ―·ζ±δ½
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.Companion.create(json,mediaType);
        //ε?δΎεOkHttpηθ―·ζ±ε¨
        OkHttpClient okHttpClient = new OkHttpClient();
        //ε?δΎεθ―·ζ±ε―Ήθ±‘
        String url = UrlUtil.url + "/equipment/applyBuy";
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        //ζrequestθ―·ζ±ε―Ήθ±‘δΌ ιη»okHttpClientθ―·ζ±ε¨οΌε°±ε―δ»₯εΎε°δΈδΈͺεε€ε₯½θΏθ‘θ―·ζ±ηCallε―Ήθ±‘
        Call call = okHttpClient.newCall(request);
        //εΌζ­₯θ―·ζ±οΌθ°η¨enqueueζΉζ³
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {//θ―·ζ±ε€±θ΄₯
                showMessage("η½η»εΌεΈΈοΌ");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {//θ―·ζ±η»ζοΌδ»£θ‘¨θ·ζε‘ε¨ιδΏ‘ζεοΌδ½δΈδΈε?θ·urlιδΏ‘ζεοΌε―θ½404
                if (response.isSuccessful()) {//ε¦ζεεΊζ―ζεηοΌζθΏθ‘ζδ½
                    String result = response.body().string();
                    Message message = gson.fromJson(result, Message.class);
                    showMessage(message.getMsg());
                }
            }
        });
    }

    /**
     * ToastεΊζε‘ε¨θΏεηmsg
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