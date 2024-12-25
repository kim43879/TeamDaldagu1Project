package com.Daldagu1.TeamDaldagu1Project.controller.User;

import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.service.User.SignService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignController {

    @Resource(name = "loginUserBean")
    @Lazy
    private UserBean loginUserBean;

    @Autowired
    private SignService signService;

    //로그인 화면
    @GetMapping("/user/login")
    public String login(Model model, @RequestParam(name = "fail", defaultValue = "false") boolean fail){
        model.addAttribute("userBean", new UserBean());
        model.addAttribute("fail", fail);

        return "user/login";
    }

    //로그인 작업
    @PostMapping("/user/login_pro")
    public String login_pro(@ModelAttribute("userBean") UserBean userBean){

        UserBean tempUserBean = signService.getUserbyId(userBean.getUser_id());

        if(tempUserBean != null){
            if(tempUserBean.getUser_pw().equals(userBean.getUser_pw())){
                loginUserBean.login(tempUserBean);
                return "user/status/login_success";
            }else {

                return "user/status/login_fail";
            }
        }else {
            return "user/status/login_fail";
        }
    }

    //로그아웃
    @GetMapping("/user/logout")
    public String logout(){
        loginUserBean.clearUserBean();
        System.out.println(loginUserBean.getUser_id());
        return "redirect:/";
    }

    //회원가입 화면
    @GetMapping("/user/join")
    public String join(Model model){
        model.addAttribute("userBean", new UserBean());

        return "user/join";
    }

    //회원가입 작업
    @PostMapping("/user/join_pro")
    public String join_pro(@Valid @ModelAttribute("userBean") UserBean userBean, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("userBean", userBean);
            for(ObjectError error : result.getAllErrors()){
                System.out.println(error.getCode());
            }

            System.out.println(userBean.getUser_birth());

            return "user/join";
        }
        signService.addUser(userBean);
        return "user/status/join_success";
    }
}
