package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.service.*;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource(name = "loginAdminBean")
    @Lazy
    private AdminBean loginAdminBean;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ReportService reportService;

    //관리자 메인페이지(대시보드)
    @GetMapping("/admin_page")
    public String admin_home(Model model) {
        model.addAttribute("user_cnt", adminService.getUserCnt());
        model.addAttribute("goods_cnt", adminService.getGoodsCnt());
        model.addAttribute("order_cnt", adminService.getOrderCnt());

        return "admin/admin_page";
    }

    @GetMapping("/login")
    public String adminLogin(Model model){
        model.addAttribute("adminBean", new AdminBean());

        return "admin/admin_login";
    }

    @PostMapping("/status/loginProc")
    public String adminLoginProc(@ModelAttribute("adminBean") AdminBean adminBean){
        AdminBean tempBean = adminService.getLoginAdminBean(adminBean);
        if(tempBean.isLoginSuccess()) {
            loginAdminBean.setAdmin_id(tempBean.getAdmin_id());
            loginAdminBean.setAdmin_pw(tempBean.getAdmin_pw());
            loginAdminBean.setLoginSuccess(tempBean.isLoginSuccess());
            return "admin/admin_page";
        }
        else
            return "admin/admin_login";
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

    @GetMapping("/report_list")
    public String reportList(Model model){
        model.addAttribute("reportList", reportService.getReportedGoodsList());
        return "admin/report_list";
    }
}//class
