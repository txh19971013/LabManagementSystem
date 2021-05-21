package com.txh;

import android.app.Application;

import lombok.Setter;

@Setter
public class MyApplication extends Application {

    public static Long teacherId = new Long(1);
    public static String realname = "红标";
    public static String college;
    public static Integer teacherSex;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
