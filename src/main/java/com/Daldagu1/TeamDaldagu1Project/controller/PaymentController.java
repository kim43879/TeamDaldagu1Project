package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.service.AddrService;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import com.Daldagu1.TeamDaldagu1Project.service.Order.OrderListService;
import com.Daldagu1.TeamDaldagu1Project.service.Order.OrderService;
import com.Daldagu1.TeamDaldagu1Project.service.User.UserMyPageService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/order/payment")
@Controller
@PropertySource("classpath:addressAndKey.properties")
public class PaymentController {

    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    @Autowired
    private UserMyPageService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderListService orderListService;

    @Autowired
    private AddrService addrService;

    @Autowired
    private GoodsService goodsService;

    @Value("${url_path}")
    private String urlPath;

    @Value("${TossClkKey}")
    private String TossKey;

    @GetMapping("/goods_buy_window")
    public String goodsBuyWindow(@RequestParam("order_idx") String order_idx,
                                 @RequestParam("message")String message,
                                 @RequestParam("addr_idx") int addr_idx,
                                 @RequestParam("amount") int amount, @RequestParam("add_point") int add_point,
                                 @RequestParam("points_to_use") int points_to_use,
                                 @RequestParam("quantity")int quantity, Model model){
        List<OrderGoodsBean> list = orderListService.getOrderGoodsList(order_idx);
        int goods_stock = goodsService.getPurchaseGoods(list.get(0).getGoods_idx()).getGoods_stock();

        if(goods_stock - quantity < 0)
            model.addAttribute("error_massage", "상품의 재고가 부족합니다.");
        else
            model.addAttribute("error_massage", "done");

        orderService.setOrderMessage(message, order_idx);


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

        model.addAttribute("urlPath", urlPath);
        model.addAttribute("TossKey", TossKey);

        model.addAttribute("add_point", add_point);
        model.addAttribute("points_to_use", points_to_use);

        model.addAttribute("quantity", quantity);
        return "order/payment/goods_buy_window";
    }

    @GetMapping("/payment_success")
    public String payment_success(@RequestParam("order_idx") String order_idx,@RequestParam("addr_idx") int addr_idx,
                                  @RequestParam("add_point") int add_point, @RequestParam("point_to_use") int point_to_use,
                                  @RequestParam("quantity") int quantity, Model model) {

        orderService.orderPaymentSuccess(order_idx, add_point, point_to_use, quantity);

        userService.calcPoint(add_point,point_to_use);

        model.addAttribute("order_idx", order_idx);
        model.addAttribute("addr_idx", addr_idx);
        model.addAttribute("used_point", point_to_use);
        return "order/payment/payment_success";
    }
    @GetMapping("/payment_fail")
    public String payment_fail(@RequestParam(name = "error_massage") String massage, Model model) {
        model.addAttribute("massage", massage);
        return "order/payment/payment_fail";
    }

    @GetMapping("/pay_complete")
    public String pay_complete(@RequestParam("order_idx") String order_idx, @RequestParam("addr_idx") int addr_idx, @RequestParam("point_to_use") int point_to_use, Model model) {

        OrderBean orderBean = orderService.getOrder(order_idx);
        UserBean userBean = userService.getUserbyIdx(loginUserBean.getUser_idx());
        List<OrderGoodsBean> list = orderListService.getOrderGoodsList(order_idx);
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
