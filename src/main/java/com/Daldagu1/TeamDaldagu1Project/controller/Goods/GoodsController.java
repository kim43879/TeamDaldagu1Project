package com.Daldagu1.TeamDaldagu1Project.controller.Goods;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.service.*;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/goods")
@Controller
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    SellerService sellerService;

    @Autowired
    OptionService optionService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    MembershipService membershipService;

    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    @GetMapping("/goods_page")
    public String goods_page(@RequestParam("goods_idx") int goods_idx,
                             @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        GoodsBean tempGoodsBean = goodsService.getPurchaseGoods(goods_idx);

        if(tempGoodsBean == null){
            return "error/goodsNotFound";
        }

        //상품페이지에 리뷰목록 등록
        List<ReviewBean> reviewList = reviewService.getReviewList(goods_idx, page);
        PageBean pageBean = reviewService.getReviewCount(goods_idx, page);

        model.addAttribute("goods", tempGoodsBean);
        model.addAttribute("seller_id",sellerService.getSellerId(tempGoodsBean.getSeller_idx()));
        model.addAttribute("goods_list", goodsService.getGoodsListByTag(tempGoodsBean.getGoods_tag()));


        model.addAttribute("reviewList", reviewList);
        model.addAttribute("pageBean", pageBean);

        model.addAttribute("user_idx", loginUserBean.getUser_idx());
        model.addAttribute("option_list", optionService.getOptionList(goods_idx));

        model.addAttribute("point", loginUserBean.getMembership_idx());

        return "goods/goods_page";
    }

    @GetMapping("/goods_buy")
    public String goodsBuy() {
        return "goods/goods_buy";
    }

    @GetMapping("/goods_seller")
    public String goodsSeller() {
        return "goods/goods_seller";
    }

    @PostMapping("/add_goods_pro")
    public String addGoodsPro(@ModelAttribute("addGoodsInfoBean")AddGoodsInfo addGoodsInfo, Model model){
        goodsService.addGoodsInfoApply(addGoodsInfo);
        return "redirect:/seller/seller_product_insert";
    }

    @PostMapping("/goods_update")
    public String goodsUpdate(@ModelAttribute("updateGoodsBean") GoodsBean updateGoodsBean, Model model){
        goodsService.updateGoodsInfo(updateGoodsBean);
        return "redirect:/seller/seller_page";
    }
}
