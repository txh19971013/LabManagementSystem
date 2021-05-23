package com.txh.json.apply;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApplyDetail {
    /**
     * 该条记录的id
     */
    private long id;
    /**
     * 创建这条申请记录的教师的id
     */
    private long teacherId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品编号
     */
    private String productNum;
    /**
     * 器材名称
     */
    private String name;
    /**
     * 器材型号
     */
    private String type;
    /**
     * 商品数量
     */
    private Integer count;
    /**
     * 商品参考价格
     */
    private Double price;
    /**
     * 器材详情
     */
    private String detail;
    /**
     * 总金额
     */
    private Double totalMoney;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 状态(0-审核中， 1-已通过， 2-已拒绝)
     */
    private Integer buyStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(Integer buyStatus) {
        this.buyStatus = buyStatus;
    }
}
