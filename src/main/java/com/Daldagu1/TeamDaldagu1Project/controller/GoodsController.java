package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import com.Daldagu1.TeamDaldagu1Project.service.SellerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/goods")
@Controller
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    SellerService sellerService;

    @Resource(name = "loginUserBean")
    @Lazy
    private UserBean loginUserBean;

    @GetMapping("/goods_page")
    public String goods_page(@RequestParam("goods_idx") int goods_idx, Model model) {
        GoodsBean tempGoodsBean = goodsService.getPurchaseGoods(goods_idx);
        model.addAttribute("goods", tempGoodsBean);
        model.addAttribute("seller_id",sellerService.getSellerId(tempGoodsBean.getSeller_idx()));

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

    @PostMapping("/search_page")
    public String goodsSearch(@ModelAttribute("searchBean") SearchBean searchBean, Model model,
                              @RequestParam(value = "page", defaultValue = "1") int page) {
        String keyword = searchBean.getSearchKeyword();

        if(searchBean.getSearchKeyword().isEmpty()) {
            searchBean.setSearchKeyword("%");
        }else{
            searchBean.setSearchKeyword("%" + searchBean.getSearchKeyword() + "%");
        }

        if(searchBean.getSearchCategory().equals("전체")){
            searchBean.setSearchCategory("%");
        }

        if(searchBean.getSearchKeyword().equals("%")){searchBean.setSearchKeyword("");}
        else {searchBean.setSearchKeyword(keyword);}
        if(searchBean.getSearchCategory().equals("%")){searchBean.setSearchCategory("전체");}

        model.addAttribute("searchGoodsList", goodsService.searchGoodsList(searchBean, page));
        model.addAttribute("page", page);
        model.addAttribute("pageBean", goodsService.getSearchPageCount(page,searchBean));
        model.addAttribute("searchBean",searchBean);
        return "goods/search_page";
    }

    @PostMapping("/add_goods_pro")
    public String addGoodsPro(@ModelAttribute("addGoodsInfoBean")AddGoodsInfo addGoodsInfo){
        goodsService.addGoodsInfoApply(addGoodsInfo);
        return "redirect:/";
    }

    @ModelAttribute("searchBean")
    public SearchBean getSearchBean() {
        return new SearchBean();
    }
}
