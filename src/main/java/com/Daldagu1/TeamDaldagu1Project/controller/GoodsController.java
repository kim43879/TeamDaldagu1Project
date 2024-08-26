package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.AddGoodsInfo;
import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/goods_page")
    public String goods_page(@RequestParam("goods_idx") int goods_idx, Model model) {

        /*List<GoodsBean> testGoods = goodsService.getPurchaseGoods(goods_idx);
        model.addAttribute("testGoods", testGoods);*/

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

    @GetMapping("/search_page")
    public String goodsSearch() {

        return "goods/search_page";
    }

    @PostMapping("/add_goods_pro")
    public String addGoodsPro(@ModelAttribute("addGoodsInfoBean")AddGoodsInfo addGoodsInfo){
        goodsService.addGoodsInfoApply(addGoodsInfo);
        return "/main";
    }
}
