package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.SellerBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SellerInfoBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.service.SellerService;
import com.Daldagu1.TeamDaldagu1Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private UserService userService;

    @GetMapping("/seller/seller_join")
    public String sellerJoinForm(@RequestParam("user_idx") int user_idx, Model model){

        UserBean userBean = userService.getUserbyIdx(user_idx);
        SellerInfoBean tempSellerBean = new SellerInfoBean();

        tempSellerBean.setUser_idx(user_idx);
        tempSellerBean.setSeller_id(userBean.getUser_id());
        tempSellerBean.setSeller_name(userBean.getUser_name());
        tempSellerBean.setSeller_phone(userBean.getUser_phone());
        tempSellerBean.setSeller_email(userBean.getUser_email());

        model.addAttribute("sellerInfoBean", tempSellerBean);
        return "seller/seller_join";
    }

    @PostMapping("/seller/seller_join_pro")
    public String sellerJoinPro(@ModelAttribute("sellerInfoBean") SellerInfoBean sellerInfoBean){

        sellerService.addSellerJoinInfo(sellerInfoBean);

        return "seller/seller_page";
    }
}
