package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewBean {
    private int review_idx;
    private String review_title;
    private int review_score;
    private String review_img;
    private String review_content;
    private int goods_page_idx;
    private int order_idx;
    private String review_writer;   //테이블에는 없는 속성(따로 가져와야함)
}
