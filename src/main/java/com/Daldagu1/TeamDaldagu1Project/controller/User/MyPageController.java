package com.Daldagu1.TeamDaldagu1Project.controller.User;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.service.*;
import com.Daldagu1.TeamDaldagu1Project.service.Order.OrderCountService;
import com.Daldagu1.TeamDaldagu1Project.service.Order.OrderListService;
import com.Daldagu1.TeamDaldagu1Project.service.Order.OrderService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MyPageController {

    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    @Autowired
    private OrderListService orderListService;

    @Autowired
    private OrderCountService orderCountService;

    @Autowired
    private WishService wishService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ReviewService reviewService;

    //마이페이지 화면
    @GetMapping("/user/user_page")
    public String user_page(Model model){
        List<OrderBean> orderList = orderListService.getOrderList(loginUserBean.getUser_idx(),-999,"USER", null);

        model.addAttribute("userBean", loginUserBean);
        model.addAttribute("wishBeanList", wishService.getUserWishList(loginUserBean.getUser_idx()));
        model.addAttribute("orderList", orderList);
        model.addAttribute("order_before_pay_cnt", orderCountService.getOrderCount(1, loginUserBean.getUser_idx()));
        model.addAttribute("orderCount", orderList.size());
        model.addAttribute("cartCount", cartService.getCartCnt(loginUserBean.getUser_idx()));
        model.addAttribute("reviewCnt", reviewService.getReviewCountForUser(loginUserBean.getUser_idx()));
        return "user/user_page";
    }

    //리뷰 조회 화면
    @GetMapping("/user/user_review")
    public String user_review(@RequestParam(name = "page", defaultValue = "1") int page, Model model){

        model.addAttribute("reviewList", reviewService.getReviewListForUser(loginUserBean.getUser_idx()));
        model.addAttribute("pageBean", reviewService.getReviewCountForUser(loginUserBean.getUser_idx(), page));

        return "user/user_review";
    }

    //포인트 조회
    @GetMapping("/user/user_point")
    public String user_point(@RequestParam(name = "page", defaultValue = "1") int page, Model model){

        model.addAttribute("loginUserBean", loginUserBean);
        model.addAttribute("orderList", orderListService.getOrderList(loginUserBean.getUser_idx(), page, "USER", null));
        model.addAttribute("pageBean", orderListService.getOrderCountForUser(loginUserBean.getUser_idx(), page, null));

        return "user/user_point";
    }
}
