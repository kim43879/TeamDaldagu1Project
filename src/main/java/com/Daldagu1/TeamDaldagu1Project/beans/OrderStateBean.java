package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStateBean {
    private int order_stat_idx;     //주문상태 기본키
    private String order_status;    //주문상태 종류 (취소/수락/완료/반품/교환)
    private int oder_idx;           //주문 기본키
}
