package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponBean {
    private int coupon_idx;
    private int min_order_price;
    private int discount;
    private int discount_type;
}
