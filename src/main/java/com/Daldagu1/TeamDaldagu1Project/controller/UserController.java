package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.AddrBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.UserMapper;
import com.Daldagu1.TeamDaldagu1Project.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller()
public class UserController {

    @Resource(name = "loginUserBean")
    @Lazy
    private UserBean loginUserBean;

    @Autowired
    UserService userService;

    @GetMapping("/user/login")
    public String login(Model model, @RequestParam(name = "fail", defaultValue = "false") boolean fail){
        model.addAttribute("userBean", new UserBean());
        model.addAttribute("fail", fail);

        return "user/login";
    }

    @PostMapping("/user/login_pro")
    public String login_pro(@ModelAttribute("userBean") UserBean userBean){

        UserBean tempUserBean = userService.getUserbyId(userBean.getUser_id());

        if(tempUserBean != null){
            if(tempUserBean.getUser_pw().equals(userBean.getUser_pw())){
                loginUserBean.setUser_idx(tempUserBean.getUser_idx());
                loginUserBean.setLoginCheck(true);
                loginUserBean.setUser_name(tempUserBean.getUser_name());
                loginUserBean.setUser_role(tempUserBean.getUser_role());
                loginUserBean.setSeller_idx(tempUserBean.getSeller_idx());

                return "user/status/login_success";
            }else {

                return "user/status/login_fail";
            }
        }else {
            return "user/status/login_fail";
        }
    }

    @GetMapping("/user/logout")
    public String logout(){
        loginUserBean.setLoginCheck(false);
        loginUserBean.setUser_id(null);
        loginUserBean.setUser_name(null);
        loginUserBean.setUser_role(null);

        return "/";
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
            for(ObjectError error : result.getAllErrors()){
                System.out.println(error.getCode());
            }

            return "user/join";
        }
        userService.addUser(userBean);
        return "user/status/join_success";
    }

    @GetMapping("/user/user_page")
    public String user_page(Model model){
        model.addAttribute("user_name", loginUserBean.getUser_name());
        return "user/user_page";
    }

    //
    @GetMapping("/user/user_order")
    public String user_order(HttpServletRequest request){

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
    public String user_addr(@RequestParam("user_idx") int user_idx, Model model){

        List<AddrBean> testAddr = userService.getExtraUserAddr(user_idx);
        //System.out.println(testAddr.get(0).getUser_addr());

        model.addAttribute("testAddr", testAddr);

        return "user/user_addr";
    }

    @PostMapping("/controller")
    public void addr_update(HttpServletRequest request) {

        String user_name = request.getParameter("user_name");
        String user_phone = request.getParameter("user_phone");
        String user_post = request.getParameter("user_post");
        String user_addr = request.getParameter("user_addr");
        String user_addr_detail = request.getParameter("user_addr_detail");

    }

    @GetMapping("/user/user_pay")
    public String user_pay(){
        return "user/user_pay";
    }
}
