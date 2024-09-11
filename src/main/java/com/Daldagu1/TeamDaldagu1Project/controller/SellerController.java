package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.service.*;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private AddrService addrService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BannerService bannerService;

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

        return "redirect:/user/user_page";
    }

    //판매자 페이지
    @GetMapping("/seller_page")
    public String sellerPage(Model model){

        int orderCnt = orderService.getOrderCnt(loginUserBean.getSeller_idx());
        List<ReviewBean> reviewList = reviewService.getReviewListForSeller(loginUserBean.getSeller_idx());

        System.out.println(reviewList.size());


        model.addAttribute("reviewList", reviewList);
        model.addAttribute("sellerBean", sellerService.getSellerbyUserIdx(loginUserBean.getSeller_idx()));
        model.addAttribute("goodsCount", goodsService.goodsCountBySellerIdx(loginUserBean.getSeller_idx()));
        model.addAttribute("orderCnt", orderCnt);
        model.addAttribute("todayOrderCount", orderService.getTodayOrderCount(loginUserBean.getSeller_idx()));

        return "seller/seller_page";
    }

    //상품추가
    @GetMapping("/seller_product_insert")
    public String sellerProductInsertForm(Model model){
        model.addAttribute("sellerBean", sellerService.getSellerbyUserIdx(loginUserBean.getSeller_idx()));
        AddGoodsInfo tempBean = new AddGoodsInfo();
        tempBean.setSeller_idx(loginUserBean.getSeller_idx());
        tempBean.setGoods_tag("장패드");
        model.addAttribute("addGoodsInfoBean", tempBean);
        return "seller/seller_product_insert";
    }

    //판매현황
    @GetMapping("/seller_order")
    public String sellerOrder(@RequestParam(name = "page", defaultValue = "1")int page, Model model){

        PageBean pageBean = orderService.getOrderCountForSeller(loginUserBean.getSeller_idx(),page);

        model.addAttribute("sellerBean", sellerService.getSellerbyUserIdx(loginUserBean.getSeller_idx()));
        model.addAttribute("orderList", orderService.getOrderListBySeller(loginUserBean.getSeller_idx(),page));
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("page", page);
        return "seller/seller_order";
    }

    //판매상태 수정
    @GetMapping("/order_read")
    public String sellerOrderRead(@RequestParam("page")int page, @RequestParam("order_idx") String order_idx, Model model){
        OrderBean orderBean = orderService.getOrder(order_idx);
        List<OrderGoodsBean> list = orderService.getOrderGoodsList(order_idx);
        int amount = 0;
        for(OrderGoodsBean bean : list){
            amount += bean.getPrice();
        }
        amount -= orderBean.getUsed_point();

        model.addAttribute("orderBean", orderBean);
        model.addAttribute("goodsList", list);
        model.addAttribute("addrBean", addrService.getAddrByAddrIdx(orderBean.getAddr_idx()));
        model.addAttribute("amount", amount);
        model.addAttribute("page", page);
        return "seller/order_read";
    }

    //상품조회
    @GetMapping("/seller_list")
    public String sellerPresent(Model model){
        model.addAttribute("sellerBean", sellerService.getSellerbyUserIdx(loginUserBean.getSeller_idx()));
        model.addAttribute("goodsList", goodsService.getMyGoodsList(loginUserBean.getSeller_idx()));
        return "seller/seller_list";
    }

    //상품목록
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

    //배너 등록
    @GetMapping("/seller_add_banner")
    public String sellerAddBanner(@RequestParam("goods_idx") int goods_idx, Model model) {

        BannerBean sellerBannerBean = new BannerBean();
        sellerBannerBean.setBanner_name(loginUserBean.getUser_id());
        sellerBannerBean.setGoods_idx(goods_idx);

        SellerBean sellerBean = sellerService.getSellerbyUserIdx(loginUserBean.getSeller_idx());

        model.addAttribute("sellerBean",sellerBean);
        model.addAttribute("addBannerInfoBean",sellerBannerBean);

        return "seller/seller_add_banner";
    }

    @PostMapping("/add_banner_pro")
    public String addBanner_pro(@ModelAttribute("addBannerInfoBean")BannerBean bannerBean) {
        bannerService.addBannerInfo(bannerBean);
        return "redirect:/seller/seller_list";
    }

    @ModelAttribute("searchBean")
    public SearchBean getSearchBean() {
        return new SearchBean();
    }

}//class
