package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchBean {

    private String searchKeyword;
    private String searchCategory = "전체";

    private int searchMinPrice = 0;
    private int searchMaxPrice = 0;

    private String sortType = "goods_name";
    private int showCount = 8;

    public void showField(){
        System.out.println("searchKeyword : " + searchKeyword);
        System.out.println("searchCategory : " + searchCategory);
        System.out.println("searchMinPrice : " + searchMinPrice);
        System.out.println("searchMaxPrice : " + searchMaxPrice);
        System.out.println("sortType : " + sortType);
        System.out.println("showCount : " + showCount);
    }
}
