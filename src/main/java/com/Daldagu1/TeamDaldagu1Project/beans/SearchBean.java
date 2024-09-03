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
}
