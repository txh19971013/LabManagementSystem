package com.txh.json.course;

import java.util.List;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class CourseInfo {
    private String msg;
    private Integer code;
    private List<Course> course;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }
}