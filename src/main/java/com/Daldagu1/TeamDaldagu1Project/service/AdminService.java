package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.AdminBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public void addAdmin(AdminBean adminBean){
        adminMapper.addAdmin(adminBean);
    }

    public AdminBean checkAdmin(String adminKey){
        return adminMapper.checkAdmin(adminKey);
    }

    //사용자 수
    public int getUserCnt() {
        return adminMapper.getUserCnt();
    }
    
    //상품 수
    public int getGoodsCnt() {
        return adminMapper.getGoodsCnt();
    }

    //주문 수
    public int getOrderCnt() {
        return adminMapper.getOrderCnt();
    }

}//class
