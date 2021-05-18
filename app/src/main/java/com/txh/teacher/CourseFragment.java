package com.txh.teacher;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.txh.R;
import com.txh.json.course.Course;
import com.txh.json.course.CourseInfo;
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

public class CourseFragment extends Fragment {

    private View rootView;

    //保存服务器数据
    private String serverData;

    Context context;

    //课表中的每个格子
    TextView course_1_1, course_1_2, course_1_3, course_1_4, course_1_5;
    TextView course_2_1, course_2_2, course_2_3, course_2_4, course_2_5;
    TextView course_3_1, course_3_2, course_3_3, course_3_4, course_3_5;
    TextView course_4_1, course_4_2, course_4_3, course_4_4, course_4_5;
    TextView course_5_1, course_5_2, course_5_3, course_5_4, course_5_5;
    TextView course_6_1, course_6_2, course_6_3, course_6_4, course_6_5;
    TextView course_7_1, course_7_2, course_7_3, course_7_4, course_7_5;
    //下拉框和按钮
    private Spinner course_weeknum, course_classroomname;
    private Button course_query;

    public CourseFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance() {
        CourseFragment fragment = new CourseFragment();
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
            rootView = inflater.inflate(R.layout.fragment_course, container, false);
        }
        initCourse();
        //默认显示第一周101实验室的情况
        defaultShow();
        course_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先重置所有格子，再来显示新的数据
                resetGrid();

                Integer weekNum = getWeekNum();
                String classroomName = getClassroomName();
                getServerData(weekNum, classroomName);
                //实例化Gson的时候要指定时间格式，不然解析Json中的时间数据的时候要报错
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                //等待子线程拿到服务器数据
                while (serverData == null) {

                }
                //反序列化,Json数据转对象
                CourseInfo courseInfo = gson.fromJson(serverData, CourseInfo.class);
                List<Course> courseList = courseInfo.getCourse();
                for (int i=0; i<courseList.size(); i++) {
                    String teacherName = courseList.get(i).getRealname();
                    String courseName = courseList.get(i).getCourseName();
                    Integer week = courseList.get(i).getWeek();
                    Integer dayTimes = courseList.get(i).getDayTimes();
                    setCourseData(teacherName, courseName, week, dayTimes);
                }
                Log.d("CourseFragment", "onClick: " + "完成一次查询操作");
                //显示完了就把上一次的数据清空，避免影响下一次的查询
                serverData = null;
                ToastUtil.showShortToast(context, "查询成功！");
            }
        });
        return rootView;
    }

    /**
     * 初始化课表中的每个格子
     */
    private void initCourse() {
        //星期一的1——5节
        course_1_1 = rootView.findViewById(R.id.course_1_1);
        course_1_2 = rootView.findViewById(R.id.course_1_2);
        course_1_3 = rootView.findViewById(R.id.course_1_3);
        course_1_4 = rootView.findViewById(R.id.course_1_4);
        course_1_5 = rootView.findViewById(R.id.course_1_5);
        //星期二的1——5节
        course_2_1 = rootView.findViewById(R.id.course_2_1);
        course_2_2 = rootView.findViewById(R.id.course_2_2);
        course_2_3 = rootView.findViewById(R.id.course_2_3);
        course_2_4 = rootView.findViewById(R.id.course_2_4);
        course_2_5 = rootView.findViewById(R.id.course_2_5);
        //星期三的1——5节
        course_3_1 = rootView.findViewById(R.id.course_3_1);
        course_3_2 = rootView.findViewById(R.id.course_3_2);
        course_3_3 = rootView.findViewById(R.id.course_3_3);
        course_3_4 = rootView.findViewById(R.id.course_3_4);
        course_3_5 = rootView.findViewById(R.id.course_3_5);
        //星期四的1——5节
        course_4_1 = rootView.findViewById(R.id.course_4_1);
        course_4_2 = rootView.findViewById(R.id.course_4_2);
        course_4_3 = rootView.findViewById(R.id.course_4_3);
        course_4_4 = rootView.findViewById(R.id.course_4_4);
        course_4_5 = rootView.findViewById(R.id.course_4_5);
        //星期五的1——5节
        course_5_1 = rootView.findViewById(R.id.course_5_1);
        course_5_2 = rootView.findViewById(R.id.course_5_2);
        course_5_3 = rootView.findViewById(R.id.course_5_3);
        course_5_4 = rootView.findViewById(R.id.course_5_4);
        course_5_5 = rootView.findViewById(R.id.course_5_5);
        //星期六的1——5节
        course_6_1 = rootView.findViewById(R.id.course_6_1);
        course_6_2 = rootView.findViewById(R.id.course_6_2);
        course_6_3 = rootView.findViewById(R.id.course_6_3);
        course_6_4 = rootView.findViewById(R.id.course_6_4);
        course_6_5 = rootView.findViewById(R.id.course_6_5);
        //星期天的1——5节
        course_7_1 = rootView.findViewById(R.id.course_7_1);
        course_7_2 = rootView.findViewById(R.id.course_7_2);
        course_7_3 = rootView.findViewById(R.id.course_7_3);
        course_7_4 = rootView.findViewById(R.id.course_7_4);
        course_7_5 = rootView.findViewById(R.id.course_7_5);
        //下拉框和按钮
        course_weeknum = rootView.findViewById(R.id.course_weeknum);
        course_classroomname = rootView.findViewById(R.id.course_classroomname);
        course_query = rootView.findViewById(R.id.course_query);

    }

    /**
     * 向课表格子中填充要显示的数据
     *
     * @param teacherName 教师名
     * @param courseName  课程名
     * @param week        星期几1--7
     * @param dayTimes    第几节课1--5
     */
    private void setCourseData(String teacherName, String courseName, Integer week, Integer dayTimes) {
        String courseInfo = teacherName + "\n\n" +courseName;
        TextView textView = getGrid(week, dayTimes);
        textView.setText(courseInfo);
        textView.setBackgroundResource(R.drawable.shape_course_selected);
    }

    /**
     * @param week     星期几1--7
     * @param dayTimes 第几节课1--5
     * @return 返回这个位置的格子组件
     */
    private TextView getGrid(Integer week, Integer dayTimes) {
        switch (week) {
            case 1:
                switch (dayTimes) {
                    case 1:
                        return course_1_1;
                    case 2:
                        return course_1_2;
                    case 3:
                        return course_1_3;
                    case 4:
                        return course_1_4;
                    case 5:
                        return course_1_5;
                }
                break;
            case 2:
                switch (dayTimes) {
                    case 1:
                        return course_2_1;
                    case 2:
                        return course_2_2;
                    case 3:
                        return course_2_3;
                    case 4:
                        return course_2_4;
                    case 5:
                        return course_2_5;
                }
                break;
            case 3:
                switch (dayTimes) {
                    case 1:
                        return course_3_1;
                    case 2:
                        return course_3_2;
                    case 3:
                        return course_3_3;
                    case 4:
                        return course_3_4;
                    case 5:
                        return course_3_5;
                }
                break;
            case 4:
                switch (dayTimes) {
                    case 1:
                        return course_4_1;
                    case 2:
                        return course_4_2;
                    case 3:
                        return course_4_3;
                    case 4:
                        return course_4_4;
                    case 5:
                        return course_4_5;
                }
                break;
            case 5:
                switch (dayTimes) {
                    case 1:
                        return course_5_1;
                    case 2:
                        return course_5_2;
                    case 3:
                        return course_5_3;
                    case 4:
                        return course_5_4;
                    case 5:
                        return course_5_5;
                }
                break;
            case 6:
                switch (dayTimes) {
                    case 1:
                        return course_6_1;
                    case 2:
                        return course_6_2;
                    case 3:
                        return course_6_3;
                    case 4:
                        return course_6_4;
                    case 5:
                        return course_6_5;
                }
                break;
            case 7:
                switch (dayTimes) {
                    case 1:
                        return course_7_1;
                    case 2:
                        return course_7_2;
                    case 3:
                        return course_7_3;
                    case 4:
                        return course_7_4;
                    case 5:
                        return course_7_5;
                }
                break;
        }
        return null;
    }

    /**
     * 从下拉框获取选择的周数
     * @return 返回选择的周数，默认1
     */
    private Integer getWeekNum() {
        String weekNum = course_weeknum.getSelectedItem().toString();
        switch (weekNum) {
            case "第1周":
                return 1;
            case "第2周":
                return 2;
            case "第3周":
                return 3;
            case "第4周":
                return 4;
            case "第5周":
                return 5;
            case "第6周":
                return 6;
            case "第7周":
                return 7;
            case "第8周":
                return 8;
            case "第9周":
                return 9;
            case "第10周":
                return 10;
            case "第11周":
                return 11;
            case "第12周":
                return 12;
            case "第13周":
                return 13;
            case "第14周":
                return 14;
            case "第15周":
                return 15;
            case "第16周":
                return 16;
            case "第17周":
                return 17;
            case "第18周":
                return 18;
            case "第19周":
                return 19;
            case "第20周":
                return 20;
        }
        return 1;
    }

    /**
     * 从下拉框获取选择的实验室名
     * @return 返回选择的实验室名，默认101
     */
    private String getClassroomName() {
        return course_classroomname.getSelectedItem().toString();
    }

    /**
     * 从服务器获取XX实验室在XX周，这一周的课程情况，然后将数据填充到课程表中
     *
     * @param weekNum 第几周
     * @param classroomName 实验室名
     */
    private void getServerData(Integer weekNum, String classroomName) {
        OkHttpClient okHttpClient = new OkHttpClient();//实例化OkHttp的请求器
        String url = UrlUtil.url + "/course/getAllCourse?week_num=" + weekNum +"&classroom_name=" + classroomName;
        Log.d("CourseFragment", "onClick: " + url);
        //实例化请求对象
        Request request = new Request.Builder()
                .url(url)
                .build();
        //把request请求对象传递给okHttpClient请求器，就可以得到一个准备好进行请求的Call对象
        Call call = okHttpClient.newCall(request);
        //异步请求，调用enqueue方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {//请求失败
                Log.d("CourseFragment", "onClick: " + e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {//请求结束，代表跟服务器通信成功，但不一定跟url通信成功，可能404
                if (response.isSuccessful()) {//如果响应是成功的，才进行操作
                    serverData = response.body().string();
                    Log.d("CourseFragment", "onResponse: " + "请求服务器数据的子线程执行完毕");
                }
            }
        });
    }

    /**
     * 重置所有格子
     */
    private void resetGrid() {
        for (int i=1; i<=7; i++) {
            for (int j=1; j<=5; j++) {
                TextView textView = getGrid(i, j);
                textView.setText(null);
                textView.setBackgroundResource(R.drawable.ripple_course);
            }
        }
    }

    /**
     * 默认显示第一周101实验室的情况
     */
    private void defaultShow() {
        getServerData(1, "101");
        //实例化Gson的时候要指定时间格式，不然解析Json中的时间数据的时候要报错
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        //等待子线程拿到服务器数据
        Log.d("CourseFragment", "onClick: " + "进入循环");
        while (serverData == null) {

        }
        Log.d("CourseFragment", "onClick: " + "循环结束");
        //反序列化,Json数据转对象
        CourseInfo courseInfo = gson.fromJson(serverData, CourseInfo.class);
        List<Course> courseList = courseInfo.getCourse();
        for (int i=0; i<courseList.size(); i++) {
            String teacherName = courseList.get(i).getRealname();
            String courseName = courseList.get(i).getCourseName();
            Integer week = courseList.get(i).getWeek();
            Integer dayTimes = courseList.get(i).getDayTimes();
            setCourseData(teacherName, courseName, week, dayTimes);
        }
        Log.d("CourseFragment", "onClick: " + "加载默认第一周101实验室的情况");
        //显示完了就把上一次的数据清空，避免影响下一次的查询
        serverData = null;
    }
}