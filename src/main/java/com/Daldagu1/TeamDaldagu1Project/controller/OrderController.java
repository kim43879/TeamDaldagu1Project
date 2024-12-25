package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.service.AddrService;
import com.Daldagu1.TeamDaldagu1Project.service.MembershipService;
import com.Daldagu1.TeamDaldagu1Project.service.Order.OrderListService;
import com.Daldagu1.TeamDaldagu1Project.service.Order.OrderService;
import com.Daldagu1.TeamDaldagu1Project.service.User.UserMyPageService;
import jakarta.annotation.Resource;
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

    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    @Autowired
    private UserMyPageService userService;

    @Autowired
    private OrderListService orderListService;

    @Autowired
    private AddrService addrService;

    @Autowired
    private MembershipService membershipService;

    //주문 결제 화면
    @GetMapping("/goods_buy")
    public String goodsBuy(@RequestParam("order_idx")String order_idx, @RequestParam("user_idx") int user_idx, Model model) {

        List<OrderGoodsBean> orderItems = orderListService.getOrderGoodsList(order_idx);

        int totalPrice = 0;
        int totalQuantity = 0;
        for (OrderGoodsBean orderItem : orderItems) {
            totalPrice += orderItem.getPrice();
            totalQuantity += orderItem.getOrder_goods_num();
        }

        float discount =  membershipService.getMembershipPoint(loginUserBean.getMembership_idx());
        int defaultAddPoint = Math.round((totalPrice / 100) * discount);

        model.addAttribute("addrList", addrService.getExtraUserAddr(user_idx));
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("userBean", userService.getUserbyIdx(user_idx));
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("order_idx", order_idx);
        model.addAttribute("currentUserPoint", loginUserBean.getCurrent_point());
        model.addAttribute("discount",discount);
        model.addAttribute("defaultAddPoint", defaultAddPoint);
        model.addAttribute("totalQuantity", totalQuantity);
        return "order/goods_buy";
    }
}
