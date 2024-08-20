package com.Daldagu1.TeamDaldagu1Project.beans;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBean {
    private int user_idx;
    private String user_id;
    private String user_pw;
    private String user_name;
    private String user_email;
    private String user_phone;
    private String user_role;       //일반유저 = D, 판매자유저 = S
    private String user_available;
    private int user_daily;
    private int user_membership_idx;

}
