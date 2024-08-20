package com.Daldagu1.TeamDaldagu1Project.beans;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBean {
    private int user_idx;

    @Size(min=2, max=10)
    @Pattern(regexp = "[a-zA-Z0-9]*")
    private String user_id;

    @Size(min=8, max=20)
    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_+\\\\-=\\\\[\\\\]{};':\\\"\\\\\\\\|,.<>\\\\/?]*")
    private String user_pw;

    @Size(min=2, max=10)
    @Pattern(regexp = "[가-힣]*")
    private String user_name;

    @Email
    private String user_email;
    private String user_phone;
    private char user_role;
    private char user_available;
    private int user_daily;
    private int user_membership_idx;

    //DB 이외
    @Size(min=8, max=20)
    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()_+\\\\-=\\\\[\\\\]{};':\\\"\\\\\\\\|,.<>\\\\/?]*")
    private String user_pw2;

}
