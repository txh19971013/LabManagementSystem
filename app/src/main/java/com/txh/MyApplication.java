package com.txh;

import android.app.Application;

import lombok.Setter;

@Setter
public class MyApplication extends Application {

    public static Long teacherId;
    public static String realname;
    public static String college;
    public static Integer teacherSex;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
