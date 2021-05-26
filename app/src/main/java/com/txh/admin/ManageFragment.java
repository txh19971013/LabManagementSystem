package com.txh.admin;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

public class ManageFragment extends Fragment {

    private View rootView;
    private Context context;

    private ImageView manage_refresh;
    private RecyclerView manage_list;

    public ManageFragment() {
        // Required empty public constructor
    }

    public static ManageFragment newInstance() {
        ManageFragment fragment = new ManageFragment();
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
            rootView = inflater.inflate(R.layout.fragment_manage, container, false);
        }
        initPager();
        //渲染界面
        getAllApply();
        return rootView;
    }

    /**
     * 初始化各组件
     */
    private void initPager() {
        manage_list = rootView.findViewById(R.id.manage_list);
        manage_refresh = rootView.findViewById(R.id.manage_refresh);
        manage_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllApply();
                showMessage("刷新成功！");
            }
        });
    }

    /**
     * 获取服务器数据，并渲染到界面上
     */
    private void getAllApply() {
        //实例化OkHttp的请求器
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = UrlUtil.url +  "/equipment/getAllApply";
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
                    List<ApplyDetail> applyListList = applyInfo.getApply();
                    setDataToRecyclerView(applyListList);
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
     * @param applyListList 申购记录的列表
     */
    private void setDataToRecyclerView(List<ApplyDetail> applyListList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //设置布局管理器
                manage_list.setLayoutManager(new LinearLayoutManager(context));
                //设置Adapter
                ApplyListAdapter applyListAdapter = new ApplyListAdapter(applyListList, context);
                manage_list.setAdapter(applyListAdapter);
            }
        });
    }
}