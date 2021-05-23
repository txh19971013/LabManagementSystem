package com.txh.json.apply;

import lombok.Setter;

@Setter
public class SubmitApply {
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
    private double price;
    /**
     * 器材详情
     */
    private String detail;
    /**
     * 总金额
     */
    private double totalMoney;
}
