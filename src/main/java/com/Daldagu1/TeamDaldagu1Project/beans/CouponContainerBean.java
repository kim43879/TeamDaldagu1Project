package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponContainerBean {
    private int coupon_container_idx;   //쿠폰함 기본키
    private int coupon_idx;             //쿠폰 키
    private int user_idx;               //소유자 키
}
