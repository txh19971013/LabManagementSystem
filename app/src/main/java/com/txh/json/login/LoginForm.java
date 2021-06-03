package com.txh.json.login;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginForm {
    String username;
    String password;
    Integer userType;
}
