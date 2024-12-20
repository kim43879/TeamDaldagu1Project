package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.service.AddrService;
import com.Daldagu1.TeamDaldagu1Project.service.MembershipService;
import com.Daldagu1.TeamDaldagu1Project.service.OrderService;
import com.Daldagu1.TeamDaldagu1Project.service.UserService;
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
    OrderService orderService;

    @Autowired
    AddrService addrService;

    @Autowired
    private UserService userService;

    @Autowired
    private MembershipService membershipService;

    //?
    @GetMapping("")
    public String myOrder(@RequestParam("order_idx") int order_idx, Model model) {
        return "/user_order";
    }

    @GetMapping("/goods_buy")
    public String goodsBuy(@RequestParam("order_idx")String order_idx, @RequestParam("user_idx") int user_idx, Model model) {

        List<OrderGoodsBean> orderItems = orderService.getOrderGoodsList(order_idx);

        int totalPrice = 0;
        for (OrderGoodsBean orderItem : orderItems) {
            totalPrice += orderItem.getPrice();
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

        return "order/goods_buy";
    }

    //주문처리 페이지 전환
    @GetMapping("/order_ready")
    public String order_ready(Model model) {
        model.addAttribute("orderList", orderService.getOrderListByOrderStat(loginUserBean.getUser_idx(), 2));
        return "order/order_ready";
    }

    @GetMapping("/order_before_check")
    public String order_before_check(Model model) {
        model.addAttribute("orderList", orderService.getOrderListByOrderStat(loginUserBean.getUser_idx(),1));
        model.addAttribute("loginUserBean", loginUserBean);
        return "order/order_before_check";
    }

    @GetMapping("/order_delivering")
    public String order_delivering(Model model) {
        model.addAttribute("orderList", orderService.getOrderListByOrderStat(loginUserBean.getUser_idx(), 3));
        return "order/order_delivering";
    }

    @GetMapping("/order_finished")
    public String order_finished(Model model) {
        model.addAttribute("orderList", orderService.getOrderListByOrderStat(loginUserBean.getUser_idx(), 4));
        return "order/order_finished";
    }

    @ModelAttribute("searchBean")
    public SearchBean getSearchBean() {
        return new SearchBean();
    }
    @GetMapping("/payment/goods_buy_window")
    public String goodsBuyWindow(@RequestParam("order_idx") String order_idx,
                                 @RequestParam("message")String message,
                                 @RequestParam("addr_idx") int addr_idx,
                                 @RequestParam("amount") int amount, @RequestParam("add_point") int add_point,
                                 @RequestParam("points_to_use") int points_to_use, Model model){

        orderService.setOrderMessage(message, order_idx);

        List<OrderGoodsBean> list = orderService.getOrderGoodsList(order_idx);
        if(list.size() == 1){
            model.addAttribute("goods_name", list.get(0).getGoods_name());
        }
        else{
            model.addAttribute("goods_name", list.get(0).getGoods_name() + "외" + (list.size()-1) + "개");
        }
        model.addAttribute("amount", amount);
        model.addAttribute("order_idx", order_idx);
        model.addAttribute("user_name",loginUserBean.getUser_name());
        model.addAttribute("addr_idx", addr_idx);

        model.addAttribute("add_point", add_point);
        model.addAttribute("points_to_use", points_to_use);
        return "order/payment/goods_buy_window";
    }

    @GetMapping("/payment/payment_success")
    public String payment_success(@RequestParam("order_idx") String order_idx,@RequestParam("addr_idx") int addr_idx,
                                  @RequestParam("add_point") int add_point, @RequestParam("point_to_use") int point_to_use, Model model) {

        orderService.orderPaymentSuccess(order_idx, add_point, point_to_use);

        userService.calcPoint(add_point,point_to_use);

        model.addAttribute("order_idx", order_idx);
        model.addAttribute("addr_idx", addr_idx);
        model.addAttribute("used_point", point_to_use);

        return "order/payment/payment_success";
    }
    @GetMapping("/payment/payment_fail")
    public String payment_fail() {
        return "order/payment/payment_fail";
    }

    @GetMapping("/payment/pay_complete")
    public String pay_complete(@RequestParam("order_idx") String order_idx, @RequestParam("addr_idx") int addr_idx, @RequestParam("point_to_use") int point_to_use, Model model) {

        OrderBean orderBean = orderService.getOrder(order_idx);
        UserBean userBean = userService.getUserbyIdx(loginUserBean.getUser_idx());
        List<OrderGoodsBean> list = orderService.getOrderGoodsList(order_idx);
        int amount = 0;
        for(OrderGoodsBean bean : list){
            amount += bean.getPrice();
        }
        amount -= point_to_use;
        AddrBean addrBean = addrService.getAddrByAddrIdx(addr_idx);

        model.addAttribute("orderBean", orderBean);
        model.addAttribute("userBean", userBean);
        model.addAttribute("goodsList", list);
        model.addAttribute("amount", amount);
        model.addAttribute("addrBean", addrBean);

        return "order/payment/pay_complete";
    }
}
