package com.Daldagu1.TeamDaldagu1Project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoodsController {

    @GetMapping("/goods/goods_page")
    public String goodsPage() {

        return "goods/goods_page";
    }
}
