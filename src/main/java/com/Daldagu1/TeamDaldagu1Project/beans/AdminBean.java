package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class AdminBean {
    private int admin_idx;      //기본키

    private String admin_id;    ////관리자 계정
    private String admin_pw;    //인증 키(UUID)

    private String admin_task;  //총괄(0), 일반(1) 구분

    private String notice_writer_idx;
}
