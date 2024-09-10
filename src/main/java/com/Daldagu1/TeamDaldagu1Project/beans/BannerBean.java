package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BannerBean {

    private int banner_idx;

    private String banner_name;
    private String banner_img;
    private MultipartFile banner_file;
    private String banner_cnt;

    private int goods_idx;
    private String goods_name;
    private int banner_status;
}
