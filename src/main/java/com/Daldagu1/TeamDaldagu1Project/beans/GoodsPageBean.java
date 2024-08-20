package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsPageBean {
    private int goods_page_idx;         //판매 페이지 기본키
    private String goods_page_title;    //판매 페이지 제목
    private String goods_page_content;  //판매 페이지 내용
    private String goods_page_img;      //판매 페이지 이미지
    private int seller_idx;             //판매자 기본키
    private int goods_idx;              //판매 상품 기본키
}
