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

    public Integer getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }

    public Integer getDayTimes() {
        return dayTimes;
    }

    public void setDayTimes(Integer dayTimes) {
        this.dayTimes = dayTimes;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
