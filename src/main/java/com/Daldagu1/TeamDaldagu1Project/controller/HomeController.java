package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Resource(name = "loginUserBean")
    @Lazy
    private UserBean loginUserBean;

    @GetMapping("/")
    public String home(){
        System.out.println(loginUserBean.isLoginCheck());


        return "main";
    }

    @GetMapping("/not_login")
    public String logout(HttpServletRequest request){

        return "not_login";
    }
}
