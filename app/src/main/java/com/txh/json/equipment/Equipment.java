package com.txh.json.equipment;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Equipment {
    private Long id;
    /**
     * 器材名称
     */
    private String name;
    /**
     * 器材型号
     */
    private String type;
    /**
     * 器材数量
     */
    private Integer count;
    /**
     * 采购时间
     */
    private Date buyDate;
    /**
     * 状态(0-未借出， 1-已借出未归还)
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
