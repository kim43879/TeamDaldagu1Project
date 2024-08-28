package com.Daldagu1.TeamDaldagu1Project.beans;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AddGoodsInfo {
    private int info_idx;
    private String goods_name;
    private String goods_tag;
    private int goods_price;
    private String goods_img;
    private MultipartFile goods_img_file;

    private String goods_info;

    private int seller_idx;
}
