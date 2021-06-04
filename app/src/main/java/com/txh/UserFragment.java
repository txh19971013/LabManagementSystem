package com.txh;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class UserFragment extends Fragment {

    private View rootView;
    private Activity context;

    private TextView user_name;
    private Button user_logout;

    private SharedPreferences mSharedPreferences;//用来读
    private SharedPreferences.Editor mEditor;//用来写
    //登录状态
    private final Integer isNull = 2;

    public UserFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
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
            rootView = inflater.inflate(R.layout.fragment_user, container, false);
        }

        user_name = rootView.findViewById(R.id.user_name);
        user_logout = rootView.findViewById(R.id.user_logout);

        //实例化SharedPreferences，第一个参数是“文件名称”，第二个参数是“创建文件的模式”
        mSharedPreferences = context.getSharedPreferences("LoginStatus", MODE_PRIVATE);
        //实例化SharedPreferences.Editor
        mEditor = mSharedPreferences.edit();

        user_name.setText(MyApplication.realname);
        user_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //写数据，键值对形式
                mEditor.putInt("loginStatus", isNull);
                mEditor.putLong("teacherId", -1L);
                mEditor.putString("realname", null);
                //提交数据，只有提交了数据才会写入到xml文件中
                mEditor.apply();
                startActivity(new Intent(context, MainActivity.class));
                context.finish();
            }
        });
        return rootView;
    }
}