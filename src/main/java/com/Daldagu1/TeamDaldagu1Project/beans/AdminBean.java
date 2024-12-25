package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class AdminBean {
    public AdminBean(){
        admin_idx = 0;
        admin_id = null;
        admin_pw = null;
        admin_task = 0;
        loginSuccess = false;
    }

    private int admin_idx;      //기본키

    private String admin_id;    ////관리자 계정
    private String admin_pw;    //인증 키(UUID)

    private int admin_task;  //총괄(1), 일반(2) 구분

    private boolean loginSuccess;

    private String notice_writer_idx;
}
