package com.txh.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.txh.R;

import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager2 vp2_teacher;

    private LinearLayout bottom_course, bottom_apply, bottom_user;
    private ImageView bottom_iv_course, bottom_iv_apply, bottom_iv_user, bottom_iv_current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        initPager();
        initBottom();
    }

    private void initPager() {
        vp2_teacher = findViewById(R.id.vp2_teacher);
        //创建参数集
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(CourseFragment.newInstance());
        fragmentList.add(ApplyFragment.newInstance());
        fragmentList.add(UserFragment.newInstance());
        //设置Adapter
        TeacherFragmentPagerAdapter teacherFragmentPagerAdapter = new TeacherFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragmentList);
        vp2_teacher.setAdapter(teacherFragmentPagerAdapter);

        //滑动到某个Pager的时候,同步选中底部导航栏对应的按钮
        vp2_teacher.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //更新底部导航栏的对应状态,先将底部导航栏当前已选中的按钮的选中状态设置为false
                bottom_iv_current.setSelected(false);
                switch (position) {
                    case 0:
                        bottom_iv_course.setSelected(true);
                        bottom_iv_current = bottom_iv_course;
                        break;
                    case 1:
                        bottom_iv_apply.setSelected(true);
                        bottom_iv_current = bottom_iv_apply;
                        break;
                    case 2:
                        bottom_iv_user.setSelected(true);
                        bottom_iv_current = bottom_iv_user;
                        break;
                }
            }
        });
    }

    private void initBottom() {

        bottom_course = findViewById(R.id.bottom_course);
        bottom_course.setOnClickListener(this);

        bottom_apply = findViewById(R.id.bottom_apply);
        bottom_apply.setOnClickListener(this);

        bottom_user = findViewById(R.id.bottom_user);
        bottom_user.setOnClickListener(this);

        bottom_iv_course = findViewById(R.id.bottom_iv_course);
        bottom_iv_apply = findViewById(R.id.bottom_iv_apply);
        bottom_iv_user = findViewById(R.id.bottom_iv_user);

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
                vp2_teacher.setCurrentItem(0);
                //更新当前的按钮选中状态
                bottom_iv_course.setSelected(true);
                bottom_iv_current = bottom_iv_course;
                break;
            case R.id.bottom_apply:
                vp2_teacher.setCurrentItem(1);
                bottom_iv_apply.setSelected(true);
                bottom_iv_current = bottom_iv_apply;
                break;
            case R.id.bottom_user:
                vp2_teacher.setCurrentItem(2);
                bottom_iv_user.setSelected(true);
                bottom_iv_current = bottom_iv_user;
                break;
        }
    }
}