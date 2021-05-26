package com.txh.json.apply;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CheckApply {
    /**
     * 该条记录的id
     */
    private Long id;
    /**in
     * 状态(0-审核中， 1-已通过， 2-已拒绝)
     */
    private Integer buyStatus;
}
