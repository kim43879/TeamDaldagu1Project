package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartBean {

    private int cart_idx; //기본키

    private int goods_idx;
    private String goods_name; //이름

    private int user_idx;
    private int goods_quantity; //상품수량
    private String selected_option; //상품옵션(테이블)
    
    private int calc_price; //장바구니 금액
    
    private int showCnt = 10; //10개씩 출력?
}
