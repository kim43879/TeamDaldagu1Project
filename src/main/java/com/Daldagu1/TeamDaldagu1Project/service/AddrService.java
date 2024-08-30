package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.AddrBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.AddrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddrService {

    @Autowired
    private AddrMapper addrMapper;

    public void addAddr(AddrBean addrBean){
        addrBean.setUser_addr(addrBean.getUser_addr1() + ", " + addrBean.getUser_addr2());
        addrBean.setAddr_main("F");
        addrBean.showField();
        addrMapper.addAddr(addrBean);
    }

    //배송지 호출
    public List<AddrBean> getExtraUserAddr(int user_idx) {

        return addrMapper.getExtraUserAddr(user_idx);
    }

    public AddrBean getAddrByAddrIdx(int addr_idx){
        return addrMapper.getAddrByAddrIdx(addr_idx);
    }

    //메인배송지 해제
    public void unMainAddr(int user_idx){
        System.out.println("원래 기본배송지 : " + addrMapper.getMainAddrIdx(user_idx));
        addrMapper.addrMainFalse(addrMapper.getMainAddrIdx(user_idx));
    }

    //배송지 업데이트
    public void updateAddr(AddrBean addrBean){
        addrBean.setUser_addr(addrBean.getUser_addr1() + ", " + addrBean.getUser_addr2());
        addrMapper.updateAddr(addrBean);
    }

    public void deleteAddr(int addr_idx){
        addrMapper.deleteAddr(addr_idx);
    }

}