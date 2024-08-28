package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.SellerBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SellerInfoBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.SellerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    @Autowired
    private SellerMapper sellerMapper;

    public void addSeller(SellerBean sellerBean) {
        sellerMapper.addSeller(sellerBean);
        sellerMapper.updateUserRoll(sellerBean.getUser_idx());
    }

    public SellerInfoBean getSellerInfo(int user_idx){
        return sellerMapper.getSellerInfo(user_idx);
    }

    public List<SellerInfoBean> getSellerInfoList(){
        return sellerMapper.getSellerInfoList();
    }

    public void addSellerJoinInfo(SellerInfoBean sellerInfoBean) {
        sellerMapper.addSellerJoinInfo(sellerInfoBean);
    }

    //판매자 ID 반환
    public String getSellerId(int seller_idx){
        return sellerMapper.getSellerId(seller_idx);
    }

    public void deleteSellerJoinInfo(int user_idx) {
        sellerMapper.deleteSellerJoinInfo(user_idx);
    }

    public SellerBean getSellerbyUserIdx(int seller_idx){
        return sellerMapper.getSellerByIdx(seller_idx);
    }
}
