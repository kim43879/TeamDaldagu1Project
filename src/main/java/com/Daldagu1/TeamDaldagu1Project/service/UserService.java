package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void addUser(UserBean userBean){
        userMapper.addUser(userBean);
    }
}
