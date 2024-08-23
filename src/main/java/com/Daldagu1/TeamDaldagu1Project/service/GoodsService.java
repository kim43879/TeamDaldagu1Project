package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    //상품 추가
    public void addGoodsInfo(GoodsBean addGoodsBean) {
        goodsMapper.addGoodsInfo(addGoodsBean);
    }

    //구매상품 호출
    public List<GoodsBean> getPurchaseGoods(int goods_idx) {
        return goodsMapper.getPurchaseGoods(goods_idx);
    }
}
