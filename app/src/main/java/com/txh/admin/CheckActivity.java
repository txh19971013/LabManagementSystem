package com.txh.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.txh.MyApplication;
import com.txh.R;
import com.txh.json.Message;
import com.txh.json.apply.CheckApply;
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

public class CheckActivity extends AppCompatActivity {

    private TextView check_productName;
    private TextView check_productNum;
    private TextView check_name;
    private TextView check_type;
    private TextView check_count;
    private TextView check_price;
    private TextView check_detail;
    private TextView check_totalMoney;
    private TextView check_teachername;
    private TextView check_createTime;
    private TextView check_buyStatus;
    private Button check_refuse;
    private Button check_accept;
    private View check_blank;
    private LinearLayout check_back;

    //要进行审核的这条申购记录的id
    Long id;

    private final Integer buyStatus_refuse = 2;
    private final Integer buyStatus_accept = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        init();
        //接收Intent和Bundle传过来的数据并渲染界面
        Bundle bundle = getIntent().getExtras();
        id = bundle.getLong("id");
        check_productName.setText(bundle.getString("productName"));
        check_productNum.setText(bundle.getString("productNum"));
        check_name.setText(bundle.getString("name"));
        check_type.setText(bundle.getString("type"));
        check_count.setText(bundle.getString("count"));
        check_price.setText(bundle.getString("price"));
        check_detail.setText(bundle.getString("detail"));
        check_totalMoney.setText(bundle.getString("totalMoney"));
        check_teachername.setText(bundle.getString("teacherName"));
        check_createTime.setText(bundle.getString("createTime"));
        Integer buyStatus = bundle.getInt("buyStatus");
        switch (buyStatus) {
            case 0:
                check_buyStatus.setText("未审核");
                //对未审核的申请要进行审核只需要显示审核按钮，不需要显示审核状态
                check_buyStatus.setVisibility(View.GONE);
                break;
            case 1:
                check_buyStatus.setText("已通过");
                check_buyStatus.setBackgroundResource(R.drawable.shape_accept_corner_10dp);
                //对已经审核过的，只需要显示审核状态，不需要显示审核按钮
                check_refuse.setVisibility(View.GONE);
                check_blank.setVisibility(View.GONE);
                check_accept.setVisibility(View.GONE);
                break;
            case 2:
                check_buyStatus.setText("已拒绝");
                check_buyStatus.setBackgroundResource(R.drawable.shape_refuse_corner_10dp);
                //对已经审核过的，只需要显示审核状态，不需要显示审核按钮
                check_refuse.setVisibility(View.GONE);
                check_blank.setVisibility(View.GONE);
                check_accept.setVisibility(View.GONE);
                break;
        }

        //点拒绝时拒绝
        check_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheckDialog(buyStatus_refuse);
            }
        });

        //点通过时通过
        check_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheckDialog(buyStatus_accept);
            }
        });

        //返回的点击事件
        check_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckActivity.super.onBackPressed();
            }
        });
    }

    private void init() {
        check_productName = findViewById(R.id.check_productName);
        check_productNum = findViewById(R.id.check_productNum);
        check_name = findViewById(R.id.check_name);
        check_type = findViewById(R.id.check_type);
        check_count = findViewById(R.id.check_count);
        check_price = findViewById(R.id.check_price);
        check_detail = findViewById(R.id.check_detail);
        check_totalMoney = findViewById(R.id.check_totalMoney);
        check_teachername = findViewById(R.id.check_teachername);
        check_createTime = findViewById(R.id.check_createTime);
        check_buyStatus = findViewById(R.id.check_buyStatus);
        check_refuse = findViewById(R.id.check_refuse);
        check_accept = findViewById(R.id.check_accept);
        check_blank = findViewById(R.id.check_blank);
        check_back = findViewById(R.id.check_back);
        //初始时，两个按钮和中间的空白，还有显示状态的TextView都可见
        check_buyStatus.setVisibility(View.VISIBLE);
        check_refuse.setVisibility(View.VISIBLE);
        check_blank.setVisibility(View.VISIBLE);
        check_accept.setVisibility(View.VISIBLE);
    }

    /**
     * 向服务器提交审核结果
     *
     * @param id 要审核的这条申购记录的id
     * @param buyStatus 审核后的状态
     */
    private void postCheck(Long id, Integer buyStatus) {
        CheckApply checkApply = new CheckApply();
        checkApply.setId(id);
        checkApply.setBuyStatus(buyStatus);
        //转为Json数据
        Gson gson = new Gson();
        String json = gson.toJson(checkApply);
        //String转请求体
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.Companion.create(json,mediaType);
        //实例化OkHttp的请求器
        OkHttpClient okHttpClient = new OkHttpClient();
        //实例化请求对象
        String url = UrlUtil.url + "/equipment/setApplyStatus";
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
                ToastUtil.showShortToast(CheckActivity.this, msg);
            }
        });
    }

    /**
     * 弹出确认提示框
     * @param buyStatus 参数时当前点击的按钮是通过还是拒绝
     */
    private void showCheckDialog(Integer buyStatus) {
        //解析视图，后面初始化组件的时候要用，不单单是为了setContentView，因为setContentView的参数可以直接传id
        View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_check, null);
        //实例化自定义Dialog
        MyCheckDialog myCheckDialog = new MyCheckDialog(this);
        //设置自定义Dialog的视图
        myCheckDialog.setContentView(view);
        myCheckDialog.setCancelable(false);
        myCheckDialog.show();

        TextView checkDialog_message = view.findViewById(R.id.checkDialog_message);
        Button checkDialog_cancel = view.findViewById(R.id.checkDialog_cancel);
        Button checkDialog_confirm = view.findViewById(R.id.checkDialog_confirm);

        if (buyStatus == buyStatus_refuse) {
            checkDialog_message.setText("确定要拒绝该申请么？");
        } else {
            checkDialog_message.setText("确定要通过该申请么？");
        }

        //点取消关闭该Dialog
        checkDialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCheckDialog.dismiss();
            }
        });

        //点确定，向服务器发起请求
        checkDialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCheck(id, buyStatus);
                myCheckDialog.dismiss();
                if (buyStatus == buyStatus_refuse) {
                    //审核成功后隐藏审核按钮，显示出当前的审核状态
                    check_buyStatus.setText("已拒绝");
                    check_buyStatus.setBackgroundResource(R.drawable.shape_refuse_corner_10dp);
                    check_buyStatus.setVisibility(View.VISIBLE);
                    check_refuse.setVisibility(View.GONE);
                    check_blank.setVisibility(View.GONE);
                    check_accept.setVisibility(View.GONE);
                } else {
                    //审核成功后隐藏审核按钮，显示出当前的审核状态
                    check_buyStatus.setText("已通过");
                    check_buyStatus.setBackgroundResource(R.drawable.shape_accept_corner_10dp);
                    check_buyStatus.setVisibility(View.VISIBLE);
                    check_refuse.setVisibility(View.GONE);
                    check_blank.setVisibility(View.GONE);
                    check_accept.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 自定义一个背景透明，高度为屏幕1/5，宽为屏幕的4/5的Dialog
     */
    class MyCheckDialog extends Dialog {

        private Context context;

        public MyCheckDialog(@NonNull Context context) {
            super(context);
            this.context = context;
            init();
        }

        /**
         * 自定义初始化方法，设置背景及宽高
         * 只要不设置setContentView，Dialog默认有一个白色的空视图，自己添加的视图是显示在Dialog的视图之上
         */
        private void init() {
            //设置视图，这里不设置的话，就有一个空白的根视图
            setContentView(R.layout.layout_dialog_check);
            //拿到Dialog的window
            Window dialogWindow = this.getWindow();
            //设置背景色为透明，
            dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
            //设置Dialog在整个页面中的位置
            dialogWindow.setGravity(Gravity.CENTER);
            //拿到Window的布局属性，因为要设置Dialog的宽高属性的话，需要封装到WindowManager.LayoutParams中
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            //获取屏幕宽度，并设置width属性
            layoutParams.width = context.getResources().getDisplayMetrics().widthPixels/5*4;
            //获取屏幕高度，并设置height属性
            layoutParams.height = context.getResources().getDisplayMetrics().heightPixels/5;
            //将WindowManager.LayoutParams中设置的参数应用到Dialog
            dialogWindow.setAttributes(layoutParams);
        }
    }
}