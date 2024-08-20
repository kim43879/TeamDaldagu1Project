package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddrBean {
    private int addr_idx;       //기본키
    private int user_idx;       //주소소유자 idx
    private String user_addr;   //주소명
    private String user_post;   //우편번호

}
