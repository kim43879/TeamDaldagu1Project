package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishBean {
    private int wish_idx;
    private int goods_idx;
    private int user_idx;

    //조인으로 가져와야하는 필드
    private String goods_name;
    private int goods_price;
    private String goods_img;
    private String goods_available;
    private String goods_tag;
}
