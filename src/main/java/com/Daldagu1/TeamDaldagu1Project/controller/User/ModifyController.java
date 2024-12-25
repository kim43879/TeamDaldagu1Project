package com.Daldagu1.TeamDaldagu1Project.controller.User;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.service.User.UserModifyService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller()
public class ModifyController {

    @Resource(name = "loginUserBean")
    @Lazy
    private UserBean loginUserBean;

    @Autowired
    UserModifyService userModifyService;

    //회원정보 수정 화면
    @GetMapping("/user/user_info")
    public String user_info() {
        return "user/user_info";
    }

    // - 문태일 수정
    //회원정보 수정프로세스
    @PostMapping("/user/user_info_pro")
    public String user_info_pro(HttpServletRequest request, Model model) {

        String user_pw = request.getParameter("user_pw");
        ;
        System.out.println(user_pw);

        if (userModifyService.checkPassword(user_pw)) {

            //UserBean modifyUserBean = userService.getModifyUserInfo(modifyUserBean);
            UserBean modifyUserBean = userModifyService.getModifyUserBean();
            if (modifyUserBean != null) {
                System.out.println("유저 이름 : " + modifyUserBean.getUser_name());
            } else {
                System.out.println("null");
            }
            model.addAttribute("modifyUserBean", modifyUserBean);
            model.addAttribute("user_idx", loginUserBean.getUser_idx());
            System.out.println(modifyUserBean.getUser_idx());

            return "user/user_modify";
        } else {
            return "user/status/user_info_fail";
        }
    }
    // - 문태일 수정
    @GetMapping("/user/user_modify")
    private String user_modify(@ModelAttribute("modifyUserBean") UserBean modifyUserBean) { //수정된 회원의 값 주입

        userModifyService.getModifyUserInfo(modifyUserBean);

        return "user/user_modify";
    }
    // - 문태일 수정
    @PostMapping("/user/update_password")
    public String updatePassword(@RequestParam("user_pw") String userPw,
                                 @RequestParam("user_pw2") String userPw2, UserBean modifyUserBean, Model model) {
        if (userPw == null || userPw2 == null || userPw.isEmpty() || !userPw.equals(userPw2)) {
            return "user/status/user_modify_fail_pw";
        }
        // 비밀번호 길이 검사
        if (userPw.length() < 8 || userPw.length() > 20 ||
                !userPw.matches(".*[A-Z].*") || !userPw.matches(".*\\d.*") ||
                !userPw.matches(".*[~!@#$%^&*()+|=].*")) {
            return "user/status/user_modify_fail_pw";
        }

        userModifyService.modifyPassword(loginUserBean.getUser_idx(), userPw);
        return "user/status/user_modify_success";
    }
    // - 문태일 수정
    @PostMapping("/user/update_email")
    public String updateEmail(@RequestParam("user_email") String userEmail, UserBean modifyUserBean, Model model) {
        if (userEmail == null || userEmail.trim().isEmpty() ||
                !userEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            return "user/status/user_modify_fail_email";
        }

        userModifyService.modifyEmail(loginUserBean.getUser_idx(), userEmail);
        return "user/status/user_modify_success";
    }
    // - 문태일 수정
    @PostMapping("/user/update_phone")
    public String updatePhone(@RequestParam("user_phone") String userPhone, UserBean modifyUserBean, Model model) {
        if (userPhone == null || userPhone.trim().isEmpty()) {
            return "user/status/user_modify_fail_phone";
        }
        if (userPhone.length() < 9 || userPhone.length() > 12 ||
                !userPhone.matches(".*[0-9].*")) {
            return "user/status/user_modify_fail_phone";

        }
        userModifyService.modifyPhone(loginUserBean.getUser_idx(), userPhone);
        return "user/status/user_modify_success";
    }
}