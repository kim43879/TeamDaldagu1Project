package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponBean {
    private int coupon_idx;         //쿠폰 기본키
    private int min_order_price;    //쿠폰 사용 최소금액
    private int discount;           //할인율
    private String discount_type;   //정가할인인가 비율할인인가 구분(정가/비율)
}
