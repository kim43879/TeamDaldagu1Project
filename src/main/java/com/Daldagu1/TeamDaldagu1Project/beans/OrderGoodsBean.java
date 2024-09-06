package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderGoodsBean {
    private int order_goods_idx;        //주문상품 기본키
    private int order_goods_num;     //주문수량
    private int goods_idx;              //상품 기본키
    private String order_idx;              //주문 기본키
    private int price;                  //주문금액
    private String selected_option;     //주문 옵션

    private String img;
    private String goods_name;
}
