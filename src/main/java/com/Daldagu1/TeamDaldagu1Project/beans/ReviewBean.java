package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ReviewBean {
    private int review_idx;         //리뷰 기본키
    private String review_title;    //리뷰 제목
    private int review_score;       //리뷰 점수
    private String review_img;      //리뷰 이미지
    private String review_content;  //리뷰 내용
    private int goods_page_idx;     //리뷰를 작성한 상품페이지의 기본키
    private int order_idx;          //리뷰작성에 필요한 주문정보

    private int goods_idx;          //리뷰 상품 기본키
    private int user_idx;           //리뷰작성자
    private String user_id;         //리뷰작성자 ID
    private MultipartFile review_file;//리뷰 사진

    private String review_writer;   //리뷰 작성자이름 //테이블에는 없는 속성(따로 가져와야함)
}
