package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBean {
    private int order_idx;              //주문 기본키
    private String order_date;          //주문일자
    private String order_delevery_addr; //배송지 주소
    private String order_delevery_num;  //송장번호
    private int user_idx;               //주문유저 기본키

    private int order_price;            //합계 주문금액
}
