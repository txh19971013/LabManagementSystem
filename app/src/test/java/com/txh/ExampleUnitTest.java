package com.txh;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.txh.json.course.Course;
import com.txh.json.course.CourseInfo;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testJson() {
        MyDate myDate = new MyDate();
        myDate.setCreateTime(new Date());
        System.out.println(myDate.getCreateTime());
        Gson gson1 = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        String str = gson1.toJson(myDate);
        System.out.println(str);


        Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        MyDate myDate2 = gson2.fromJson(str, MyDate.class);
        System.out.println(myDate2.getCreateTime());
    }

    @Test
    public void testIntParse() {
        String i = "";
        System.out.println(Integer.parseInt(i.toString()));
    }
}