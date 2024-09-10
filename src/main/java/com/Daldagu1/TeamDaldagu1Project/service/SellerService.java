package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.PageBean;
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

    //판매자 추가
    public void addSeller(SellerBean sellerBean) {
        sellerMapper.addSeller(sellerBean);                     //DB에 정보 추가
        sellerMapper.updateUserRoll(sellerBean.getUser_idx());  //유저DB의 role 업데이트
    }
    //DB에서 판매자 등록 정보 가져오기(판매자 등록 승인에 사용)
    public SellerInfoBean getSellerInfo(int user_idx){
        return sellerMapper.getSellerInfo(user_idx);
    }

    //DB에서 판매자 등록 목록 가져오기
    public List<SellerInfoBean> getSellerInfoList(){
        return sellerMapper.getSellerInfoList();
    }

    //판매자 등록 정보 DB 추가
    public void addSellerJoinInfo(SellerInfoBean sellerInfoBean) {
        sellerMapper.addSellerJoinInfo(sellerInfoBean);
    }

    //판매자 ID 반환
    public String getSellerId(int seller_idx){
        return sellerMapper.getSellerId(seller_idx);
    }
    //판매자 정보 삭제
    public void deleteSellerJoinInfo(int user_idx) {
        sellerMapper.deleteSellerJoinInfo(user_idx);
    }
    //판매자 정보 가져오기(idx)
    public SellerBean getSellerbyUserIdx(int seller_idx){
        return sellerMapper.getSellerByIdx(seller_idx);
    }

    public void deSignUp(int seller_idx){
        sellerMapper.deSignUp(seller_idx);
    }
}
