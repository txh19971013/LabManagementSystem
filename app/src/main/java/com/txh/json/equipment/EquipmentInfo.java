package com.txh.json.equipment;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EquipmentInfo {
    private String msg;
    private Integer code;
    private List<Equipment> AllEquipment;
}
