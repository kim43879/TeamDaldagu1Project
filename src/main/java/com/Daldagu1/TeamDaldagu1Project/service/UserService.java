package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.AddrBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.AddrMapper;
import com.Daldagu1.TeamDaldagu1Project.mapper.MembershipMapper;
import com.Daldagu1.TeamDaldagu1Project.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PropertySource("classpath:imgPath.properties")
@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    @Value("${imgPath}")
    private String ImgPath;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddrMapper addrMapper;

    @Autowired
    private MembershipMapper membershipMapper;

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

        addrMapper.addAddr(addrBean);
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

                int membership_idx = tempLoginUserBean.getMembership_idx();
                String userMembership = membershipMapper.getMembership(membership_idx);
                tempLoginUserBean.setMembership(userMembership);

                System.out.println(tempLoginUserBean.getSeller_idx());
            }
        }

        return tempLoginUserBean;
    }

    ///////////////////////////////// 문태일 수정
    //  getModifyUser , modifyUser 삭제

    //정보수정  - 문태일
    public void getModifyUserInfo(UserBean modifyUserBean) {

        UserBean tempModifyUserBean = userMapper.getModifyUserInfo(loginUserBean.getUser_idx()); //temp는 DB에서 갖고 온 UserBean
        modifyUserBean.setUser_id(tempModifyUserBean.getUser_id());
        modifyUserBean.setUser_name(tempModifyUserBean.getUser_name());
        modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
    }

    //정보수정(DB) - 문태일
    public void modifyUserInfo(UserBean modifyUserBean) { //매개변수로 modifyUserBean(Controller에서 올려보냄)

        modifyUserBean.setUser_idx(loginUserBean.getUser_idx()); //현재 로그인한 회원의 기본키를 넣음
        userMapper.modifyUserInfo(modifyUserBean);
    }
    public boolean checkPassword(String user_pw){

        String user_id = loginUserBean.getUser_id();
        System.out.println("로그인한 아이디 : " + user_id);
        String check = userMapper.checkPassword(user_id, user_pw);
        if(check != null){
            return true;


        }else{
            return false;
        }
    }

    // 비밀번호 수정 - 문태일
    public void modifyPassword(int userIdx, String newPassword) {
        userMapper.modifyPassword(userIdx, newPassword);
    }

    // 이메일 수정 - 문태일
    public void modifyEmail(int userIdx, String newEmail) {
        userMapper.modifyEmail(userIdx, newEmail);
    }

    // 연락처 수정 - 문태일
    public void modifyPhone(int userIdx, String newPhone) {

        String phone1 = newPhone.substring(0,3);
        String phone2 = newPhone.substring(3,7);
        String phone3 = newPhone.substring(7,11);
        String phone = phone1 + "-" + phone2 + "-" + phone3;

        userMapper.modifyPhone(userIdx, phone);
    }

    //info 비밀번호 - 문태일
    public UserBean getModifyUserBean(){
        if (loginUserBean == null) {
            throw new IllegalStateException("loginUserBean is null");
        }//

        System.out.println("로그인한 회원의 IDX " + loginUserBean.getUser_idx());
        return userMapper.getModifyUserBean(loginUserBean.getUser_idx());
    }
    

}

