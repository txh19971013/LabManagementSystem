package com.txh.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.txh.MyApplication;
import com.txh.R;
import com.txh.json.apply.ApplyDetail;
import com.txh.json.apply.ApplyInfo;
import com.txh.utils.ToastUtil;
import com.txh.utils.UrlUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApplyFragment extends Fragment {

    private View rootView;
    private Context context;

    private ImageView apply_add;
    private ImageView apply_refresh;
    private RecyclerView apply_list;

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
        //渲染界面
        getApplyHistory(MyApplication.teacherId);
        return rootView;
    }

    /**
     * 初始化各组件
     */
    private void initPager() {
        apply_add = rootView.findViewById(R.id.apply_add);
        apply_list = rootView.findViewById(R.id.apply_list);
        apply_refresh = rootView.findViewById(R.id.apply_refresh);
        apply_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点加号跳转到提交申请界面
                startActivity(new Intent(context, AddApplyActivity.class));
            }
        });
        apply_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApplyHistory(MyApplication.teacherId);
                showMessage("刷新成功！");
            }
        });
    }

    /**
     * 获取服务器数据，并渲染到界面上
     * @param teacherId
     */
    private void getApplyHistory(Long teacherId) {
        //实例化OkHttp的请求器
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = UrlUtil.url +  "/equipment/getApplyById?teacherId=1";
        //实例化请求对象，get请求一般是不上传参数的，get请求要上传参数的话就在get后面写?key=value，多个参数用&分隔
        Request request = new Request.Builder()
                .url(url)
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
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    ApplyInfo applyInfo = gson.fromJson(result, ApplyInfo.class);
                    List<ApplyDetail> applyDetailList = applyInfo.getTheTeacherApply();
                    setDataToRecyclerView(applyDetailList);
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(context, msg);
            }
        });
    }

    /**
     * 给RecyclerView填充数据
     *
     * @param applyDetailList 申购记录的列表
     */
    private void setDataToRecyclerView(List<ApplyDetail> applyDetailList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //设置布局管理器
                apply_list.setLayoutManager(new LinearLayoutManager(context));
                //设置Adapter
                ApplyHistoryAdapter applyHistoryAdapter = new ApplyHistoryAdapter(applyDetailList, context);
                apply_list.setAdapter(applyHistoryAdapter);
            }
        });
    }
}