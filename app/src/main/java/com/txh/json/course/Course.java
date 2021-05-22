package com.txh.json.course;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Course {
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
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 上课老师
     */
    private String username;
    /**
     * 老师真实名称
     */
    private String realname;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}