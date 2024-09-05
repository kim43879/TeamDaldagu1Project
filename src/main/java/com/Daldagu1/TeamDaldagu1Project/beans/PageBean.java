package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageBean {

    private int min;
    private int max;

    private int prevPage;
    private int nextPage;
    private int pageCnt;

    private int currentPage;

    public PageBean(int goodsCnt, int currentPage, int goodsPageCnt, int paginationCnt) {
        this.currentPage = currentPage;
        this.pageCnt = goodsCnt/goodsPageCnt;

        if(goodsCnt % goodsPageCnt > 0){
            pageCnt++;
        }
        min = ((currentPage-1)/goodsPageCnt) * goodsPageCnt + 1;

        max = min + paginationCnt - 1;
        if (max > this.pageCnt){
            max = this.pageCnt;
        }

        prevPage = min - 1;
        if(prevPage < 1){
            prevPage = 1;
        }

        nextPage = max + 1;
        if(nextPage > pageCnt){
            nextPage = pageCnt;
        }
    }
}
