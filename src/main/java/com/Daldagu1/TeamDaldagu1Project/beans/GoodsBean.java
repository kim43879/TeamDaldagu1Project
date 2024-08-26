package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsBean {
    private int goods_idx;           //기본키
    private String goods_name;      //상품 이름
    private String goods_tag;       //상품 카테고리
    private int goods_price;        //상품 가격
    private String goods_img;       //상품 이미지
    private int goods_stock;        //상품 재고
    private String goods_available; //상품 삭제여부

    private int seller_idx;

    private boolean selling_true;   //상품 판매여부
}
