package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.SellerInfoBean;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import com.Daldagu1.TeamDaldagu1Project.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminContoller {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/admin/dev_admin_page")
    public String devAdminPage(Model model) {
        List<SellerInfoBean> sellerInfoList = sellerService.getSellerInfoList();

        model.addAttribute("sellerInfoList",sellerInfoList);

        return "admin/dev_admin_page";
    }
    @GetMapping("/admin/dev_admin_gooods_page")
    public String devAdminGoodsPage(Model model) {

        model.addAttribute("addGoodsInfoList", goodsService.getAddGoodsInfoList());

        return "admin/dev_admin_goods_page";
    }
}