package com.Daldagu1.TeamDaldagu1Project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/goods")
@Controller
public class GoodsController {

    @GetMapping("/goods_page")
    public String goodsPage() {
        return "goods/goods_page";
    }
    @GetMapping("/goods_buy")
    public String goodsBuy() {
        return "goods/goods_buy";
    }
}
