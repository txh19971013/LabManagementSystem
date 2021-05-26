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
import com.txh.R;
import com.txh.json.apply.ApplyInfo;
import com.txh.json.equipment.Equipment;
import com.txh.json.equipment.EquipmentInfo;
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

public class EquipmentListFragment extends Fragment {

    private View rootView;
    private Context context;

    private ImageView equipment_refresh;
    private RecyclerView equipment_list;

    public EquipmentListFragment() {
        // Required empty public constructor
    }

    public static EquipmentListFragment newInstance() {
        EquipmentListFragment fragment = new EquipmentListFragment();
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
            rootView = inflater.inflate(R.layout.fragment_equipment_list, container, false);
        }
        initPager();
        //渲染界面
        getAllEquipment();
        return rootView;
    }

    /**
     * 初始化各组件
     */
    private void initPager() {
        equipment_refresh = rootView.findViewById(R.id.equipment_refresh);
        equipment_list = rootView.findViewById(R.id.equipment_list);
        equipment_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllEquipment();
                showMessage("刷新成功！");
            }
        });
    }

    /**
     * 获取服务器数据，并渲染到界面上
     */
    private void getAllEquipment() {
        //实例化OkHttp的请求器
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = UrlUtil.url +  "/equipment/getAllEquipment";
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
                    EquipmentInfo equipmentInfo = gson.fromJson(result, EquipmentInfo.class);
                    List<Equipment> equipmentList = equipmentInfo.getAllEquipment();
                    setDataToRecyclerView(equipmentList);
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
     * @param equipmentList 器材列表
     */
    private void setDataToRecyclerView(List<Equipment> equipmentList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //设置布局管理器
                equipment_list.setLayoutManager(new LinearLayoutManager(context));
                //设置Adapter
                EquipmentListAdapter equipmentListAdapter = new EquipmentListAdapter(equipmentList, context);
                equipment_list.setAdapter(equipmentListAdapter);
            }
        });
    }
}