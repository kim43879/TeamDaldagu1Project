package com.Daldagu1.TeamDaldagu1Project.service.User;

import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserModifyService {

    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    @Autowired
    private UserMapper userMapper;;

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
