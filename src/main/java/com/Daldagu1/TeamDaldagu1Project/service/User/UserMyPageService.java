package com.Daldagu1.TeamDaldagu1Project.service.User;

import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.*;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@PropertySource("classpath:imgPath.properties")
@Service
public class UserMyPageService {
    private static final Logger log = LoggerFactory.getLogger(UserMyPageService.class);

    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    @Value("${imgPath}")
    private String ImgPath;

    @Autowired
    private UserMapper userMapper;

    //유저 아이디 반환(id 중복체크에 사용)

    //idx로 유저객체 반환
    public UserBean getUserbyIdx(int user_idx){
        return userMapper.getUserbyIdx(user_idx);
    }

    private String saveProfileImageFile(MultipartFile profileImg){
        String file_name = System.currentTimeMillis() + "_" + profileImg.getOriginalFilename();
        try{
            profileImg.transferTo(new File(ImgPath + "/user_profile/" + file_name));
        } catch (Exception e){
            e.printStackTrace();
        }
        return file_name;
    }

    public void updateProfile(int user_idx, MultipartFile profileImg){
        String profile_name = saveProfileImageFile(profileImg);
        userMapper.updateProfile(profile_name, user_idx);

        loginUserBean.setUser_profile_img(profile_name);
    }

    public void calcPoint(int add_point, int point_to_use){
        int nextTotalPoint = loginUserBean.getTotal_user_point() + add_point;
        int nextUsedPoint = loginUserBean.getUsed_user_point() + point_to_use;
        userMapper.calcPoint(nextTotalPoint, nextUsedPoint, loginUserBean.getUser_idx());

        UserBean tempUserBean = userMapper.getPoint(loginUserBean.getUser_idx());
        loginUserBean.setTotal_user_point(tempUserBean.getTotal_user_point());
        loginUserBean.setUsed_user_point(tempUserBean.getUsed_user_point());
        int currentTotalPoint = tempUserBean.getTotal_user_point();
        int currentUsedPoint = tempUserBean.getUsed_user_point();
        loginUserBean.setCurrent_point(currentTotalPoint - currentUsedPoint);
    }
}

