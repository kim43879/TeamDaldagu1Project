package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller()
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/login")
    public String login(){
        return "user/login";
    }

    @PostMapping("/user/login_pro")
    public String login_pro(){
        return "main";
    }

    @GetMapping("/user/join")
    public String join(){
        return "user/join";
    }

    @PostMapping("/user/join_pro")
    public String join_pro(){

        return "login";
    }

    @GetMapping("/user/user_page")
    public String user_page(){
        return "user/user_page";
    }

    @GetMapping("/user/user_order")
    public String user_order(){
        return "user/user_order";
    }
    @GetMapping("/user/user_present")
    public String user_present(){
        return "user/user_present";
    }
    @GetMapping("/user/user_review")
    public String user_review(){
        return "user/user_review";
    }
    @GetMapping("/user/user_wish")
    public String user_wish(){
        return "user/user_wish";
    }
    @GetMapping("/user/user_cart")
    public String user_cart(){
        return "user/user_cart";
    }
    @GetMapping("/user/user_info")
    public String user_info(){
        return "user/user_info";
    }
    @GetMapping("/user/user_addr")
    public String user_addr(){
        return "user/user_addr";
    }
    @GetMapping("/user/user_pay")
    public String user_pay(){
        return "user/user_pay";
    }

}
