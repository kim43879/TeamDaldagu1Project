package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.OrderGoodsBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import com.Daldagu1.TeamDaldagu1Project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    //?
    @GetMapping("")
    public String myOrder(@RequestParam("order_idx") int order_idx, Model model) {

        List<OrderGoodsBean> testOrderList = orderService.getOrderList(order_idx);
        model.addAttribute("testOrderList", testOrderList);

        for(OrderGoodsBean order : testOrderList) {
            System.out.println(order.getOrder_idx());
        }

        return "/user_order";
    }

    //주문처리 페이지 전환
    @GetMapping("/order_ready")
    public String order_ready() {
        return "order/order_ready";
    }

    @GetMapping("/order_before_check")
    public String order_before_check() {
        return "order/order_before_check";
    }

    @GetMapping("/order_delivering")
    public String order_delivering() {
        return "order/order_delivering";
    }

    @GetMapping("/order_finished")
    public String order_finished() {
        return "order/order_finished";
    }

    @ModelAttribute("searchBean")
    public SearchBean getSearchBean() {
        return new SearchBean();
    }
}
