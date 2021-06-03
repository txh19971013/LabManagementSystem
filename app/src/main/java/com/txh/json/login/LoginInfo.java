package com.txh.json.login;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginInfo {
    private String msg;
    private Integer code;
    private User user;
}
