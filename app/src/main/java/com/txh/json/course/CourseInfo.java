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
}