package com.txh.json.apply;


import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApplyInfo {
    private String msg;
    private Integer code;
    private List<ApplyDetail> apply;
}
