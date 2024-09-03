package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.CartBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.service.CartService;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import com.Daldagu1.TeamDaldagu1Project.service.SellerService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CartController {

    @Resource(name = "loginUserBean")
    @Lazy
    private UserBean loginUserBean;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CartService cartService;

    @ModelAttribute("searchBean")
    public SearchBean getSearchBean() {
        return new SearchBean();
    }

    //장바구니 추가
    @GetMapping("/user/add_user_cart")
    public String add_cart(@RequestParam("goods_name") String goods_name,
                           @RequestParam("goods_quantity") int goods_quantity,
                           @RequestParam("selected_option") String selected_option,
                           @RequestParam("result_cart") boolean result_cart, Model model) {

        cartService.addUserCart(loginUserBean.getUser_idx(), goods_name, goods_quantity, selected_option);

        if(result_cart) {
            List<CartBean> cartBeanList = cartService.getCartList(loginUserBean.getUser_idx());
            model.addAttribute("cartBeanList", cartBeanList);

            return "redirect:/user/user_cart";
        }
        return "goods/goods_page";
    }

    //장바구니 출력
    @GetMapping("/user/user_cart")
    public String user_cart(@RequestParam("user_idx") int user_idx, Model model) {

        List<CartBean> cartBeanList = cartService.getCartList(user_idx);
        int totalPrice = cartService.getTotalPrice(user_idx);

        model.addAttribute("cartBeanList", cartBeanList);
        model.addAttribute("totalPrice", totalPrice);

        return "user/user_cart";
    }

}//class
