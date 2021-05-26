package com.txh.json.course;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SaveCourse {
    /**
     * 周数(第几周)
     */
    private Integer weekNum;
    /**
     * 时间(每天第几节课)
     */
    private Integer dayTimes;
    /**
     * 星期(1-星期一，2-星期二...etc..)
     */
    private Integer week;
    /**
     * 教师id
     */
    private Long teacherId;
    /**
     * 教室名称
     */
    private String classroomName;
    /**
     * 课程名称
     */
    private String courseName;
}
