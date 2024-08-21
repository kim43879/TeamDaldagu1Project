package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller()
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/login")
    public String login(Model model){
        model.addAttribute("userBean", new UserBean());

        return "user/login";
    }

    @PostMapping("/user/login_pro")
    public String login_pro(@ModelAttribute("userBean") UserBean userBean){

        UserBean tempUserBean = userService.getUserbyId(userBean.getUser_id());

        if(tempUserBean != null){
            if(tempUserBean.getUser_pw().equals(userBean.getUser_pw())){

                return "user/status/login_success";
            }else {

                return "user/login";
            }
        }else {
            return "user/login";
        }
    }

    @GetMapping("/user/join")
    public String join(Model model){
        model.addAttribute("userBean", new UserBean());
        return "user/join";
    }

    @PostMapping("/user/join_pro")
    public String join_pro(@Valid @ModelAttribute("userBean") UserBean userBean, BindingResult result,Model model){
        if(result.hasErrors()){
            model.addAttribute("userBean", userBean);

            return "user/join";
        }
        userService.addUser(userBean);
        return "user/status/join_success";
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
