package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeBean {

    private int notice_idx;

    private String notice_title;
    private String notice_writer_idx;
    private String notice_text;

    private String notice_date;

    //private String notice_file;
    //private MultipartFile notice_img;

}
