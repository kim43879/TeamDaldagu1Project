package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    //상품 추가
    public void addGoodsInfo(GoodsBean addGoodsBean) {
        goodsMapper.addGoodsInfo(addGoodsBean);
    }
}
