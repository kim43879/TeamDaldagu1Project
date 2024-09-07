package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBean {
    private String order_idx;           //주문 기본키
    private String order_date;          //주문일자
    private int user_idx;               //주문유저 기본키
    private int seller_idx;             //판매자 idx
    private int addr_idx;               //배송지정보 기본키
    private int order_stat;             //주문 상태 코드
    private String order_message;       //주문메세지

    private String sample_img;          //대표이미지
    private int quantity;               //주문 수량
    private int order_price;            //합계 주문금액
    private String order_statText;      //주문상태 메세지
    
    private String goods_name;          //대표 상품 이름
    private int goods_idx;
}
