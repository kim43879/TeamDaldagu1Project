package com.Daldagu1.TeamDaldagu1Project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class SellerController {

    @GetMapping("/seller/seller_join")
    public String sellerJoinForm(){
        return "seller/seller_join";
    }
}
