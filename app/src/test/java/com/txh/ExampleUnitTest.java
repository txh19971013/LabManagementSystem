package com.txh;

import android.util.Log;

import com.google.gson.Gson;
import com.txh.json.course.Course;
import com.txh.json.course.CourseInfo;

import org.junit.Test;

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
        String json = "{\"msg\":\"success\",\"code\":0,\"course\":[{\"weekNum\":1,\"dayTimes\":1,\"week\":1,\"teacherId\":1,\"classroomName\":\"101\",\"courseName\":\"物理实验\",\"createTime\":\"2021-05-06 12:17:07\",\"updateTime\":\"2021-05-11 23:16:17\",\"username\":\"hongbiao\",\"realname\":\"红标\"},{\"weekNum\":1,\"dayTimes\":2,\"week\":5,\"teacherId\":6,\"classroomName\":\"101\",\"courseName\":\"Android企业级项目综合实践\",\"createTime\":\"2021-05-06 17:21:51\",\"updateTime\":\"2021-05-11 23:18:49\",\"username\":\"kongzi\",\"realname\":\"孔子\"},{\"weekNum\":1,\"dayTimes\":5,\"week\":1,\"teacherId\":5,\"classroomName\":\"101\",\"courseName\":\"企业级应用开发\",\"createTime\":\"2021-05-11 23:18:44\",\"updateTime\":\"2021-05-11 23:18:44\",\"username\":\"wanganshi\",\"realname\":\"王安石\"},{\"weekNum\":1,\"dayTimes\":5,\"week\":2,\"teacherId\":8,\"classroomName\":\"101\",\"courseName\":\"Java核心逻辑实践\",\"createTime\":\"2021-05-11 23:20:33\",\"updateTime\":\"2021-05-11 23:20:33\",\"username\":\"quyuan\",\"realname\":\"屈原\"},{\"weekNum\":1,\"dayTimes\":1,\"week\":3,\"teacherId\":6,\"classroomName\":\"101\",\"courseName\":\"Android 项目综合实践\",\"createTime\":\"2021-05-11 23:22:10\",\"updateTime\":\"2021-05-11 23:22:10\",\"username\":\"kongzi\",\"realname\":\"孔子\"},{\"weekNum\":1,\"dayTimes\":1,\"week\":4,\"teacherId\":5,\"classroomName\":\"101\",\"courseName\":\"Java核心逻辑\",\"createTime\":\"2021-05-11 23:23:38\",\"updateTime\":\"2021-05-11 23:23:38\",\"username\":\"wanganshi\",\"realname\":\"王安石\"},{\"weekNum\":1,\"dayTimes\":4,\"week\":4,\"teacherId\":7,\"classroomName\":\"101\",\"courseName\":\"web开发技术\",\"createTime\":\"2021-05-11 23:23:38\",\"updateTime\":\"2021-05-11 23:23:38\",\"username\":\"luxun\",\"realname\":\"周树人\"},{\"weekNum\":1,\"dayTimes\":2,\"week\":5,\"teacherId\":8,\"classroomName\":\"101\",\"courseName\":\"面向对象程序设计\",\"createTime\":\"2021-05-11 23:26:25\",\"updateTime\":\"2021-05-11 23:26:25\",\"username\":\"quyuan\",\"realname\":\"屈原\"},{\"weekNum\":1,\"dayTimes\":1,\"week\":6,\"teacherId\":11,\"classroomName\":\"101\",\"courseName\":\"数字电路实验\",\"createTime\":\"2021-05-12 19:24:34\",\"updateTime\":\"2021-05-12 19:24:34\",\"username\":\"dufu\",\"realname\":\"杜甫\"},{\"weekNum\":1,\"dayTimes\":1,\"week\":6,\"teacherId\":11,\"classroomName\":\"101\",\"courseName\":\"数字电路实验\",\"createTime\":\"2021-05-12 20:16:25\",\"updateTime\":\"2021-05-12 20:16:25\",\"username\":\"dufu\",\"realname\":\"杜甫\"}]}";
        System.out.println(json);
        Gson gson = new Gson();
        CourseInfo courseInfo = gson.fromJson(json, CourseInfo.class);
        List<Course> courseList = courseInfo.getCourse();
        for (int i=0; i<courseList.size(); i++) {
            String teacherName = courseList.get(i).getRealname();
            String courseName = courseList.get(i).getCourseName();
            Integer week = courseList.get(i).getWeek();
            Integer dayTimes = courseList.get(i).getDayTimes();
            System.out.println(teacherName);
            System.out.println(courseName);
            System.out.println(week);
            System.out.println(dayTimes);
            System.out.println("\n");
        }
    }
}