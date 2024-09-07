package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import com.Daldagu1.TeamDaldagu1Project.service.OrderService;
import com.Daldagu1.TeamDaldagu1Project.service.SellerService;
import com.Daldagu1.TeamDaldagu1Project.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping("/seller")
public class SellerController {
    @Resource(name = "loginUserBean")
    @Lazy
    private UserBean loginUserBean;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/seller_join")
    public String sellerJoinForm(Model model){

        UserBean userBean = userService.getUserbyIdx(loginUserBean.getUser_idx());
        SellerInfoBean tempSellerBean = new SellerInfoBean();

        tempSellerBean.setUser_idx(loginUserBean.getUser_idx());
        tempSellerBean.setSeller_id(userBean.getUser_id());
        tempSellerBean.setSeller_name(userBean.getUser_name());
        tempSellerBean.setSeller_phone(userBean.getUser_phone());
        tempSellerBean.setSeller_email(userBean.getUser_email());

        model.addAttribute("sellerInfoBean", tempSellerBean);
        return "seller/seller_join";
    }

    @PostMapping("/seller_join_pro")
    public String sellerJoinPro(@ModelAttribute("sellerInfoBean") SellerInfoBean sellerInfoBean){

        sellerService.addSellerJoinInfo(sellerInfoBean);

        return "user/user_page";
    }

    @GetMapping("/seller_page")
    public String sellerPage(Model model){
        model.addAttribute("sellerBean", sellerService.getSellerbyUserIdx(loginUserBean.getSeller_idx()));
        model.addAttribute("goodsCount", goodsService.goodsCountBySellerIdx(loginUserBean.getSeller_idx()));

        return "seller/seller_page";
    }

    @GetMapping("/seller_product_insert")
    public String sellerProductInsertForm(Model model){
        model.addAttribute("sellerBean", sellerService.getSellerbyUserIdx(loginUserBean.getSeller_idx()));
        AddGoodsInfo tempBean = new AddGoodsInfo();
        tempBean.setSeller_idx(loginUserBean.getSeller_idx());
        model.addAttribute("addGoodsInfoBean", tempBean);
        return "seller/seller_product_insert";
    }

    @GetMapping("/seller_order")
    public String sellerOrder(Model model){
        model.addAttribute("sellerBean", sellerService.getSellerbyUserIdx(loginUserBean.getSeller_idx()));
        model.addAttribute("orderList", orderService.getOrderListBySeller(loginUserBean.getSeller_idx()));
        return "seller/seller_order";
    }

    @GetMapping("/seller_list")
    public String sellerPresent(Model model){
        model.addAttribute("sellerBean", sellerService.getSellerbyUserIdx(loginUserBean.getSeller_idx()));
        model.addAttribute("goodsList", goodsService.getMyGoodsList(loginUserBean.getSeller_idx()));
        return "seller/seller_list";
    }

    @GetMapping("/seller_product_read")
    public String sellerProductRead(@RequestParam("goods_idx") int goods_idx, Model model){
        model.addAttribute("goodsBean", goodsService.getPurchaseGoods(goods_idx));
        model.addAttribute("sellerBean", sellerService.getSellerbyUserIdx(loginUserBean.getSeller_idx()));
        return "seller/seller_product_read";
    }

    @GetMapping("/seller_product_delete")
    public String sellerProductDelete(){
        return "seller/seller_product_delete";
    }

    @ModelAttribute("searchBean")
    public SearchBean getSearchBean() {
        return new SearchBean();
    }
}
