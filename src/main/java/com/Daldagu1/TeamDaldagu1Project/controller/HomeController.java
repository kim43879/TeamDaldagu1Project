package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.service.AdminService;
import com.Daldagu1.TeamDaldagu1Project.service.BannerService;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Resource(name = "loginUserBean")
    @Lazy
    private UserBean loginUserBean;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/")
    public String home(Model model){
        String[] tag = {"장패드", "펫 용품", "컵"};
        System.out.println(loginUserBean.isLoginCheck());
        List<GoodsBean> goodsList;
        for (int i = 1; i < 4; i++){
            goodsList = goodsService.getGoodsListByTag(tag[i-1]);
            model.addAttribute("goodsList" + i, goodsList);
        }
        model.addAttribute("bannerList", bannerService.getBannerList());

        return "main";
    }

    //관리자 메인
    @GetMapping("/admin_page")
    public String admin_home(Model model) {

        model.addAttribute("user_cnt", adminService.getUserCnt());
        model.addAttribute("goods_cnt", adminService.getGoodsCnt());
        model.addAttribute("order_cnt", adminService.getOrderCnt());

        return "admin_page";
    }

    @GetMapping("/not_login")
    public String logout(HttpServletRequest request){

        return "not_login";
    }
    @GetMapping("/not_seller")
    public String not_seller(){
        return "not_seller";
    }

    @ModelAttribute("searchBean")
    public SearchBean getSearchBean() {
        return new SearchBean();
    }

}
