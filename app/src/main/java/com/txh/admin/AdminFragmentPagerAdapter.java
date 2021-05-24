package com.txh.admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdminFragmentPagerAdapter extends FragmentStateAdapter {
    List<Fragment> fragmentList = new ArrayList<>();

    /**
     * 构造方法
     */
    public AdminFragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragmentList) {
        super(fragmentManager, lifecycle);
        this.fragmentList = fragmentList;//传入所有的Fragment
    }

    /**
     *
     * Adapter的目的是适配Fragment，这个方法就是告诉系统要适配一个什么样的Fragment，
     * 所以要返回一个Fragment
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    /**
     *
     * 告诉系统有多少个Fragment
     */
    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
