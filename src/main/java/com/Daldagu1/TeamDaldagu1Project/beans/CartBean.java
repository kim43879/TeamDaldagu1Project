package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartBean {

    private int cart_idx; //기본키

    private int goods_idx;
    private String goods_name; //이름

    private int seller_idx;

    private int user_idx;
    private int goods_quantity; //상품수량
    private String selected_option; //상품옵션(테이블)

    private int cart_price; //장바구니 금액(옵션 합산)

    private int option_idx; //옵션 idx

    public void addInfo(CartBean cartBean){
        this.goods_idx = cartBean.getGoods_idx();
        this.user_idx = cartBean.getUser_idx();
        this.goods_quantity = cartBean.getGoods_quantity();
        this.goods_name = cartBean.getGoods_name();
        this.selected_option = cartBean.getSelected_option();
        this.cart_price = cartBean.getCart_price();
    }

}
