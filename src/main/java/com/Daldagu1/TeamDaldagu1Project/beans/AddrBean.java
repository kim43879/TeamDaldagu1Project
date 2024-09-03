package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddrBean {
    private int addr_idx;       //기본키
    private int user_idx;       //주소소유자 idx
    private String user_addr;
    private String user_addr1;   //주소명
    private String user_addr2;  //상세주소
    private String user_post;   //우편번호

    //추가
    private String addr_phone;
    private String addr_name; //
    private String addr_main; //기본배송지 여부(t/f)

    private String message;

    public void showField(){
        System.out.println("addr_idx : " + addr_idx);
        System.out.println("user_idx : " + user_idx);
        System.out.println("user_addr : " + user_addr);
        System.out.println("user_addr1 : " + user_addr1);
        System.out.println("user_addr2 : " + user_addr2);
        System.out.println("user_post : " + user_post);
        System.out.println("addr_phone : " + addr_phone);
        System.out.println("addr_name : " + addr_name);
        System.out.println("addr_main : " + addr_main);
        System.out.println("message : " + message);
    }
}
