package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderGoodsBean {
    private int order_goods_idx;        //주문상품 기본키
    private int order_goods_number;     //주문수량
    private int goods_idx;              //상품 기본키
    private int order_idx;              //주문 기본키
}
