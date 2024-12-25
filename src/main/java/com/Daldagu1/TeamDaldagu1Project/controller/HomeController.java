package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.service.AdminService;
import com.Daldagu1.TeamDaldagu1Project.service.BannerService;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Arrays;
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
        String[] tags = goodsService.getMainTags(loginUserBean.getUser_idx());
        if(tags.length > 3)
            tags = Arrays.copyOfRange(tags, 0, 3);
        System.out.println(loginUserBean.isLoginCheck());
        List<List<GoodsBean>> goodsList = new ArrayList<List<GoodsBean>>();

        for(String tag : tags) {
            List<GoodsBean> list = new ArrayList<GoodsBean>();
            list = goodsService.getGoodsListByTag(tag);
            goodsList.add(list);
        }
        model.addAttribute("goodsListList", goodsList);
        model.addAttribute("tags", tags);
        model.addAttribute("bannerList", bannerService.getBannerList());

        return "main";
    }

    @GetMapping("/not_login")
    public String logout(HttpServletRequest request){

        return "not_login";
    }

    @GetMapping("/not_seller")
    public String not_seller(){
        return "not_seller";
    }
}
