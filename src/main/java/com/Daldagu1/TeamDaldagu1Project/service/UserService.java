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
import java.util.Map;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;

    public void addUser(UserBean userBean){
        System.out.println(userBean.getUser_phone());
        String phone1 = userBean.getUser_phone().substring(0,3);
        String phone2 = userBean.getUser_phone().substring(3,7);
        String phone3 = userBean.getUser_phone().substring(7,11);
        String phone = phone1 + "-" + phone2 + "-" + phone3;

        System.out.println(phone1);
        System.out.println(phone2);
        System.out.println(phone3);
        userBean.setUser_phone(phone);
        userMapper.addUser(userBean);

        AddrBean addrBean = new AddrBean();

        addrBean.setUser_idx(userMapper.getUserIdx(userBean.getUser_id()));
        String userAddr = userBean.getUser_addr1() + ", " + userBean.getUser_addr2();
        addrBean.setUser_addr(userAddr);
        addrBean.setUser_post(userBean.getUser_post());

        System.out.println("주소 : " + addrBean.getUser_addr());
        System.out.println("우편번호 : " + addrBean.getUser_post());
        System.out.println("유저idx : " + addrBean.getUser_idx());

        userMapper.addAddr(addrBean);
    }

    public String getUserId(String user_id){
        return userMapper.getUserId(user_id);
    }

    public UserBean getUserbyIdx(int user_idx){
        return userMapper.getUserbyIdx(user_idx);
    }
    public UserBean getUserbyId(String user_id){
        return userMapper.getUserbyId(user_id);
    }

}
