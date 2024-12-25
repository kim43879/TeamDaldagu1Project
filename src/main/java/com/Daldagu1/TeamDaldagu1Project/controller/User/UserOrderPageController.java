package com.Daldagu1.TeamDaldagu1Project.controller.User;

import com.Daldagu1.TeamDaldagu1Project.beans.OrderBean;
import com.Daldagu1.TeamDaldagu1Project.beans.PageBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.service.Order.OrderCountService;
import com.Daldagu1.TeamDaldagu1Project.service.Order.OrderListService;
import com.Daldagu1.TeamDaldagu1Project.service.Order.OrderService;
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
@RequestMapping("/user")
public class UserOrderPageController {

    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderListService orderListService;

    @Autowired
    private OrderCountService orderCountService;

    //주문정보 화면
    @GetMapping("/user_order")
    public String user_order(@RequestParam(name = "page", defaultValue = "1")int page, Model model){
        System.out.println(page);
        List<OrderBean> list = orderListService.getOrderList(loginUserBean.getUser_idx(), page, "USER", null);

        PageBean pageBean = orderListService.getOrderCountForUser(loginUserBean.getUser_idx(), page, null);

        model.addAttribute("orderList", list);

        model.addAttribute("order_before_pay_cnt", orderCountService.getOrderCount(1, loginUserBean.getUser_idx()));
        model.addAttribute("order_ready_cnt", orderCountService.getOrderCount(2, loginUserBean.getUser_idx()));
        model.addAttribute("order_going_cnt", orderCountService.getOrderCount(3, loginUserBean.getUser_idx()));
        model.addAttribute("order_complete_cnt",orderCountService.getOrderCount(4, loginUserBean.getUser_idx()));

        model.addAttribute("pageBean", pageBean);
        model.addAttribute("page", page);
        return "user/user_order";
    }

    //배송 준비 목록
    @GetMapping("/order_ready")
    public String order_ready(@RequestParam(name = "page", defaultValue = "1")int page, Model model) {
        List<OrderBean> list = orderListService.getOrderList(loginUserBean.getUser_idx(), page, "ORDER_STAT", 2);

        model.addAttribute("orderList", list);

        model.addAttribute("pageBean", orderListService.getOrderCountForUser(loginUserBean.getUser_idx(), page, 2));
        model.addAttribute("page", page);

        return "order/order_ready";
    }

    //결제 미진행 목록
    @GetMapping("/order_before_check")
    public String order_before_check(@RequestParam(name = "page", defaultValue = "1")int page, Model model) {
        List<OrderBean> list = orderListService.getOrderList(loginUserBean.getUser_idx(), page, "ORDER_STAT", 1);

        model.addAttribute("orderList", list);

        model.addAttribute("pageBean", orderListService.getOrderCountForUser(loginUserBean.getUser_idx(), page, 1));
        model.addAttribute("page", page);
        return "order/order_before_check";
    }

    //배송 중 목록
    @GetMapping("/order_delivering")
    public String order_delivering(@RequestParam(name = "page", defaultValue = "1")int page, Model model) {
        List<OrderBean> list = orderListService.getOrderList(loginUserBean.getUser_idx(), page, "ORDER_STAT", 3);

        model.addAttribute("orderList", list);

        model.addAttribute("pageBean", orderListService.getOrderCountForUser(loginUserBean.getUser_idx(), page, 3));
        model.addAttribute("page", page);
        return "order/order_delivering";
    }

    //배송 완료 목록
    @GetMapping("/order_finished")
    public String order_finished(@RequestParam(name = "page", defaultValue = "1")int page, Model model) {
        List<OrderBean> list = orderListService.getOrderList(loginUserBean.getUser_idx(), page, "ORDER_STAT", 4);

        model.addAttribute("orderList", list);

        model.addAttribute("pageBean", orderListService.getOrderCountForUser(loginUserBean.getUser_idx(), page, 4));
        model.addAttribute("page", page);
        return "order/order_finished";
    }
}
