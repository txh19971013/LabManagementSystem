package com.txh.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.txh.R;
import com.txh.UserFragment;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager2 vp2_admin;

    private LinearLayout bottom_course, bottom_manage, bottom_equipment, bottom_user;
    private ImageView bottom_iv_course, bottom_iv_manage, bottom_iv_user, bottom_iv_equipment, bottom_iv_current;
    private TextView bottom_tv_course, bottom_tv_manage, bottom_tv_equipment, bottom_tv_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initPager();
        initBottom();
    }

    private void initPager() {
        vp2_admin = findViewById(R.id.vp2_admin);
        //创建参数集
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(CourseFragment.newInstance());
        fragmentList.add(ManageFragment.newInstance());
        fragmentList.add(EquipmentListFragment.newInstance());
        fragmentList.add(UserFragment.newInstance());
        //设置Adapter
        AdminFragmentPagerAdapter adminFragmentPagerAdapter = new AdminFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragmentList);
        vp2_admin.setAdapter(adminFragmentPagerAdapter);

        //滑动到某个Pager的时候,同步选中底部导航栏对应的按钮
        vp2_admin.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //更新底部导航栏的对应状态,先将底部导航栏当前已选中的按钮的选中状态设置为false
                bottom_iv_current.setSelected(false);
                switch (position) {
                    case 0:
                        bottom_iv_course.setSelected(true);
                        bottom_iv_current = bottom_iv_course;

                        bottom_tv_course.setTextColor(Color.parseColor("#1297dd"));
                        bottom_tv_manage.setTextColor(Color.parseColor("#99000000"));
                        bottom_tv_equipment.setTextColor(Color.parseColor("#99000000"));
                        bottom_tv_user.setTextColor(Color.parseColor("#99000000"));
                        break;
                    case 1:
                        bottom_iv_manage.setSelected(true);
                        bottom_iv_current = bottom_iv_manage;

                        bottom_tv_course.setTextColor(Color.parseColor("#99000000"));
                        bottom_tv_manage.setTextColor(Color.parseColor("#1297dd"));
                        bottom_tv_equipment.setTextColor(Color.parseColor("#99000000"));
                        bottom_tv_user.setTextColor(Color.parseColor("#99000000"));
                        break;
                    case 2:
                        bottom_iv_equipment.setSelected(true);
                        bottom_iv_current = bottom_iv_equipment;

                        bottom_tv_course.setTextColor(Color.parseColor("#99000000"));
                        bottom_tv_manage.setTextColor(Color.parseColor("#99000000"));
                        bottom_tv_equipment.setTextColor(Color.parseColor("#1297dd"));
                        bottom_tv_user.setTextColor(Color.parseColor("#99000000"));
                        break;
                    case 3:
                        bottom_iv_user.setSelected(true);
                        bottom_iv_current = bottom_iv_user;

                        bottom_tv_course.setTextColor(Color.parseColor("#99000000"));
                        bottom_tv_manage.setTextColor(Color.parseColor("#99000000"));
                        bottom_tv_equipment.setTextColor(Color.parseColor("#99000000"));
                        bottom_tv_user.setTextColor(Color.parseColor("#1297dd"));
                        break;
                }
            }
        });
    }

    private void initBottom() {

        bottom_course = findViewById(R.id.bottom_course);
        bottom_course.setOnClickListener(this);

        bottom_manage = findViewById(R.id.bottom_manage);
        bottom_manage.setOnClickListener(this);

        bottom_equipment = findViewById(R.id.bottom_equipment);
        bottom_equipment.setOnClickListener(this);

        bottom_user = findViewById(R.id.bottom_user);
        bottom_user.setOnClickListener(this);

        bottom_iv_course = findViewById(R.id.bottom_iv_course);
        bottom_iv_manage = findViewById(R.id.bottom_iv_manage);
        bottom_iv_equipment = findViewById(R.id.bottom_iv_equipment);
        bottom_iv_user = findViewById(R.id.bottom_iv_user);

        bottom_tv_course = findViewById(R.id.bottom_tv_course);
        bottom_tv_manage = findViewById(R.id.bottom_tv_manage);
        bottom_tv_equipment = findViewById(R.id.bottom_tv_equipment);
        bottom_tv_user = findViewById(R.id.bottom_tv_user);

        //初始化底部导航栏初始的选中状态
        bottom_iv_course.setSelected(true);
        bottom_iv_current = bottom_iv_course;
    }

    /**
     * 点击底部导航栏的按钮的时候,同步切换到对应的Pager
     */
    @Override
    public void onClick(View view) {
        //先将底部导航栏当前已选中的按钮的选中状态设置为false
        bottom_iv_current.setSelected(false);
        switch (view.getId()) {
            case R.id.bottom_course:
                //切换到对应的Pager
                vp2_admin.setCurrentItem(0);
                //更新当前的按钮选中状态
                bottom_iv_course.setSelected(true);
                bottom_iv_current = bottom_iv_course;

                bottom_tv_course.setTextColor(Color.parseColor("#1297dd"));
                bottom_tv_manage.setTextColor(Color.parseColor("#99000000"));
                bottom_tv_equipment.setTextColor(Color.parseColor("#99000000"));
                bottom_tv_user.setTextColor(Color.parseColor("#99000000"));
                break;
            case R.id.bottom_manage:
                vp2_admin.setCurrentItem(1);
                bottom_iv_manage.setSelected(true);
                bottom_iv_current = bottom_iv_manage;

                bottom_tv_course.setTextColor(Color.parseColor("#99000000"));
                bottom_tv_manage.setTextColor(Color.parseColor("#1297dd"));
                bottom_tv_equipment.setTextColor(Color.parseColor("#99000000"));
                bottom_tv_user.setTextColor(Color.parseColor("#99000000"));
                break;
            case R.id.bottom_equipment:
                vp2_admin.setCurrentItem(2);
                bottom_iv_equipment.setSelected(true);
                bottom_iv_current = bottom_iv_equipment;

                bottom_tv_course.setTextColor(Color.parseColor("#99000000"));
                bottom_tv_manage.setTextColor(Color.parseColor("#99000000"));
                bottom_tv_equipment.setTextColor(Color.parseColor("#1297dd"));
                bottom_tv_user.setTextColor(Color.parseColor("#99000000"));
                break;
            case R.id.bottom_user:
                vp2_admin.setCurrentItem(3);
                bottom_iv_user.setSelected(true);
                bottom_iv_current = bottom_iv_user;

                bottom_tv_course.setTextColor(Color.parseColor("#99000000"));
                bottom_tv_manage.setTextColor(Color.parseColor("#99000000"));
                bottom_tv_equipment.setTextColor(Color.parseColor("#99000000"));
                bottom_tv_user.setTextColor(Color.parseColor("#1297dd"));
                break;
        }
    }
}