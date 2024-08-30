package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.AddrBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;

    public void addUser(UserBean userBean){

        String phone1 = userBean.getUser_phone().substring(0,3);
        String phone2 = userBean.getUser_phone().substring(3,7);
        String phone3 = userBean.getUser_phone().substring(7,11);
        String phone = phone1 + "-" + phone2 + "-" + phone3;

        userBean.setUser_phone(phone);
        userMapper.addUser(userBean);

        AddrBean addrBean = new AddrBean();

        addrBean.setUser_idx(userMapper.getUserIdx(userBean.getUser_id()));
        String userAddr = userBean.getUser_addr1() + ", " + userBean.getUser_addr2();
        addrBean.setUser_addr(userAddr);
        addrBean.setUser_post(userBean.getUser_post());

        addrBean.setAddr_name("기본");
        addrBean.setAddr_main("T");
        addrBean.setAddr_phone(userBean.getUser_phone());

        userMapper.addAddr(addrBean);
    }

    public void addAddr(AddrBean addrBean){
        addrBean.setUser_addr(addrBean.getUser_addr1() + ", " + addrBean.getUser_addr2());
        addrBean.setAddr_main("F");
        addrBean.showField();
        userMapper.addAddr(addrBean);
    }

    public String getUserId(String user_id){
        return userMapper.getUserId(user_id);
    }

    public UserBean getUserbyIdx(int user_idx){
        return userMapper.getUserbyIdx(user_idx);
    }
    public UserBean getUserbyId(String user_id){
        UserBean tempLoginUserBean = userMapper.getUserbyId(user_id);
        if(tempLoginUserBean != null) {
            if (tempLoginUserBean.getUser_role().equals("S")) {
                tempLoginUserBean.setSeller_idx(userMapper.getSellerIdx(tempLoginUserBean.getUser_idx()));
                System.out.println(tempLoginUserBean.getSeller_idx());
            }
        }

        return tempLoginUserBean;
    }
    //배송지 호출
    public List<AddrBean> getExtraUserAddr(int user_idx) {

        return userMapper.getExtraUserAddr(user_idx);
    }
    //배송지 업데이트
    public void getUpdateAddr(int user_idx) {
        userMapper.getUpdateAddr(user_idx);
    }
}

