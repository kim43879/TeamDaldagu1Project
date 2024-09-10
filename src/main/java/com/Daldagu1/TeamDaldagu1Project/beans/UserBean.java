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
    private int membership_idx;
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

    private String membership;
    
    private int total_user_point;   //총 적립금
    private int used_user_point;    //사용한 적립금
    private int current_point;      //사용 가능한 적립금

    //각 필드를 출력하는 메서드(seller_idx, loginCheck, id_check 제외)
    public void printUserBean(){
        System.out.println("아이디 : " + this.user_id);
        System.out.println("비밀번호 : " + this.user_pw);
        System.out.println("이름 : " + this.user_name);
        System.out.println("이메일 : " + this.user_email);
        System.out.println("연락처 : " + this.user_phone);
        System.out.println("생년월일 : " + this.user_birth);
        System.out.println("주소 : " + this.user_addr1);
        System.out.println("상세주소 : " + this.user_addr2);
        System.out.println("우편번호 : " + this.user_post);
    }
    //각 필드 초기화메서드(로그아웃)
    public void clearUserBean(){
        this.loginCheck = false;
        this.user_id = "";
        this.user_pw = "";
        this.user_name = "";
        this.user_email = "";
        this.user_phone = "";
        this.user_birth = "";
        this.user_profile_img = "";
        this.user_profile_text = "";
        this.seller_idx = 0;
        this.user_daily = 0;
        this.membership_idx = 0;
        this.user_role = null;
        this.user_idx = 0;
        this.membership = null;
    }
    //필드에 값 담기 매개변수는 UserBean
    public void login(UserBean tempUserBean){
        this.loginCheck = true;
        this.user_id = tempUserBean.getUser_id();
        this.user_pw = tempUserBean.getUser_pw();
        this.user_name = tempUserBean.getUser_name();
        this.user_email = tempUserBean.getUser_email();
        this.user_phone = tempUserBean.getUser_phone();
        this.user_birth = tempUserBean.getUser_birth();
        this.user_profile_img = tempUserBean.getUser_profile_img();
        this.user_profile_text = tempUserBean.getUser_profile_text();
        this.user_role = tempUserBean.getUser_role();
        this.user_idx = tempUserBean.getUser_idx();
        this.seller_idx = tempUserBean.getSeller_idx();
        this.membership_idx = tempUserBean.getMembership_idx();
        this.user_daily = tempUserBean.getUser_daily();
        switch (tempUserBean.getMembership_idx()){
            case 1: this.membership = "Bronze";
                    break;
            case 2: this.membership = "Silver";
                    break;
            case 3: this.membership = "Gold";
                    break;
            case 4: this.membership = "Platinum";
                    break;
            default: this.membership = "UnMembers";
        }
        this.total_user_point = tempUserBean.getTotal_user_point();
        this.used_user_point = tempUserBean.getUsed_user_point();
        this.current_point = total_user_point - used_user_point;
    }


}

