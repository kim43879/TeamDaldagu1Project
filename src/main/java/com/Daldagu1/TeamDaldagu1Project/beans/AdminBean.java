package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminBean {
    private int admin_idx;      //기본키
    private String admin_id;    //관리자 이름
    private String admin_pw;    //인증 키(UUID)
    private String admin_task;  //총괄/일반 구분
}
