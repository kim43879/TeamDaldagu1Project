package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import com.Daldagu1.TeamDaldagu1Project.beans.PageBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.GoodsMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Value("${page.paginationCnt}")
    private int paginationCnt;

    public List<GoodsBean> searchGoodsList(SearchBean searchBean, int page){

        int start = (page - 1) * searchBean.getShowCount();

        RowBounds rowBounds = new RowBounds(start,(searchBean.getShowCount()));

        if(searchBean.getSearchCategory().equals("전체")){
            searchBean.setSearchCategory("%");
        }
        if(searchBean.getSearchMaxPrice() == 0){
            searchBean.setSearchMaxPrice(300000);
        }

        if(searchBean.getSortType().equals("goods_price1")){
            return goodsMapper.searchGoodsListOrder_price1(searchBean, rowBounds);
        }else if(searchBean.getSortType().equals("goods_price2")){
            return goodsMapper.searchGoodsListOrder_price2(searchBean, rowBounds);
        }

        return goodsMapper.searchGoodsList(searchBean,rowBounds);
    }

    public PageBean getSearchPageCount(int currentPage, SearchBean searchBean){
        if(searchBean.getSearchCategory().equals("goods_price1")){
            return new PageBean(goodsMapper.searchGoodsListOrder_priceCnt(searchBean), currentPage, searchBean.getShowCount(), paginationCnt);
        }
        else if(searchBean.getSearchCategory().equals("goods_price2")){
            return new PageBean(goodsMapper.searchGoodsListOrder_price2Cnt(searchBean), currentPage, searchBean.getShowCount(), paginationCnt);
        }
        return new PageBean(goodsMapper.searchGoodsListCnt(searchBean),currentPage,searchBean.getShowCount(),paginationCnt);
    }

    public int getTotalGoodsCnt(SearchBean searchBean){
        if(searchBean.getSearchCategory().equals("goods_price1")){
            return goodsMapper.searchGoodsListOrder_priceCnt(searchBean);
        }
        else if(searchBean.getSearchCategory().equals("goods_price2")){
            return goodsMapper.searchGoodsListOrder_price2Cnt(searchBean);
        }
        return goodsMapper.searchGoodsListCnt(searchBean);
    }
}
