package com.Daldagu1.TeamDaldagu1Project.controller.Rest;

import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.service.*;
import com.Daldagu1.TeamDaldagu1Project.service.User.SignService;
import com.Daldagu1.TeamDaldagu1Project.service.User.UserMyPageService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignRestController {

    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    @Autowired
    private UserMyPageService userService;

    @Autowired
    private SignService signService;

    @Autowired
    private SellerService sellerService;

    //회원가입(아이디 중복확인)
    @PostMapping("/rest/idCheck")
    public String IdCheck(@RequestParam("user_id")String user_id){
        String db_id = signService.getUserId(user_id);
        if(db_id == null) {
            return "true";
        }else{
            return "false";
        }
    }

    //로그아웃
    @PostMapping("/rest/deSignUp")
    public void deSignUp(@RequestParam("user_idx") int user_idx){
        signService.deSignUp(user_idx);
        if(userService.getUserbyIdx(user_idx).getUser_role() == "S"){
            sellerService.deSignUp(1);
        }
        loginUserBean.clearUserBean();
    }
}
