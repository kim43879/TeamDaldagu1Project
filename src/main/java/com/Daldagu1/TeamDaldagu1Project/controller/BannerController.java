package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/main_banner")
    public String addBannerInfo(Model model) {

        model.addAttribute("bannerList", bannerService.adminGetBannerList());

        return "admin/main_banner";
    }

}//class
