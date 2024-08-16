package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsBean {
    private int goods_idx;
    private String goods_name;
    private String goods_tag;
    private int goods_price;
    private String goods_img;
    private int goods_stock;
    private char goods_available;
}
