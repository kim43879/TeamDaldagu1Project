package com.Daldagu1.TeamDaldagu1Project.beans;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBean {
    private int user_idx;

    public UserBean(){}

    public UserBean(boolean loginCheck){
        this.loginCheck = loginCheck;
    }

    @NotBlank(message = "아이디는 필수로 입력해야합니다.")
    @Size(min=2, max=10, message = "아이디는 2글자 이상, 10글자 이하여야합니다.")
    @Pattern(regexp = "[a-zA-Z0-9]*", message = "아이디는 영문 대소문자 및 숫자만 사용가능합니다.")
    private String user_id;

    @NotBlank(message = "비밀번호는 필수로 입력해야합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
            message = "비밀번호는 8글자 이상 20글자 이하이어야하며 대문자/숫자/특수문자가 반드시 1개이상 포함되어야합니다.")
    private String user_pw;

    @NotBlank(message = "이름은 필수로 입력해야합니다.")
    @Size(min=2, max=10, message = "이름은 2글자 이상, 10글자 이하여야합니다.")
    @Pattern(regexp = "[가-힣]*", message = "이름은 한글만 입력가능합니다.")
    private String user_name;

    @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "이메일의 형식은 example@exaple.com입니다.")
    private String user_email;
    @NotBlank(message = "연락처는 필수 입력사항입니다.")
    private String user_phone;
    private String user_role;       //일반유저 = D, 판매자유저 = S
    private String user_available;
    private int user_daily;
    private int user_membership_idx;
    @NotBlank(message = "생년월일은 필수 입력사항입니다.")
    private String user_birth;

    private String user_profile_img;
    private String user_profile_text;

    //DB 이외
    @NotBlank(message = "비밀번호 확인은 필수로 입력해야합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
            message = "비밀번호는 8글자 이상 20글자 이하이어야하며 대문자/숫자/특수문자가 반드시 1개이상 포함되어야합니다.")
    private String user_pw2;

    @NotEmpty(message = "주소는 필수 입력사항입니다.")
    private String user_addr1;
    private String user_addr2;
    private String user_post;

    @NotBlank(message = "아이디 중복확인을 해야합니다.")
    private String id_check;

    private boolean loginCheck;

    private int seller_idx;

}

