package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private AdminService adminService;

    //관리자 메인페이지(대시보드)
    @GetMapping("/dev_admin_main")
    public String mainAdminPage(Model model) {

        model.addAttribute("user_cnt", adminService.getUserCnt());
        model.addAttribute("goods_cnt", adminService.getGoodsCnt());
        model.addAttribute("order_cnt", adminService.getOrderCnt());

        return "admin_page";
    }
    
    //판매자 등록
    @GetMapping("/dev_admin_page")
    public String devAdminPage(Model model) {
        List<SellerInfoBean> sellerInfoList = sellerService.getSellerInfoList();

        model.addAttribute("sellerInfoList",sellerInfoList);

        return "admin/dev_admin_page";
    }

    //상품 승인
    @GetMapping("/dev_admin_goods_page")
    public String devAdminGoodsPage(Model model) {

        model.addAttribute("addGoodsInfoList", goodsService.getAddGoodsInfoList());

        return "admin/dev_admin_goods_page";
    }

}//class
