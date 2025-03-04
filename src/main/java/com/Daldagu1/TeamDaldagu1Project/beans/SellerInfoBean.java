package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SellerInfoBean {
    private int info_idx;
    private String seller_name;
    private String seller_id;
    private String seller_email;
    private String seller_phone;
    private String seller_page_title;
    private String calc_account;
    private String goods_tag;
    private String sample_img;

    private MultipartFile sample_img_file;

    private int user_idx;

}
