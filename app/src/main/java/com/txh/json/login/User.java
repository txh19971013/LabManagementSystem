package com.txh.json.login;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户类型(0-学生， 1-教师)
     */
    private String userType;
    /**
     * 性别(0-男，1-女)
     */
    private Integer sex;
    /**
     * 学院
     */
    private String college;
    /**
     * 专业
     */
    private String major;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
