package com.txh.json.apply;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApplyHistory {
    /**
     * 该条记录的id
     */
    private long id;
    /**
     * 创建这条申请记录的教师的id
     */
    private long teacherId;
    /**
     * 器材名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 状态(0-审核中， 1-已通过， 2-已拒绝)
     */
    private Integer buyStatus;
}
