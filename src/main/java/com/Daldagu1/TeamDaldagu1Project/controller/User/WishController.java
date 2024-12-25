package com.Daldagu1.TeamDaldagu1Project.controller.User;

import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.beans.WishBean;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import com.Daldagu1.TeamDaldagu1Project.service.SellerService;
import com.Daldagu1.TeamDaldagu1Project.service.WishService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WishController {

    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    @Autowired
    private WishService wishService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SellerService sellerService;

    //관심상품등록
    @GetMapping("/user/add_user_wish")
    public String add_user_wish(@RequestParam("goods_idx") int goods_idx,
                                @RequestParam("user_idx") int user_idx,
                                @RequestParam("result") boolean result, Model model){
        if(wishService.checkWish(goods_idx,user_idx)) {
            wishService.addUserWish(goods_idx, user_idx);
        }

        if(result) {
            List<WishBean> wishBeanList = wishService.getUserWishList(user_idx);
            model.addAttribute("wishBeanList", wishBeanList);

            return "redirect:/user/user_wish";
        }
        GoodsBean tempGoodsBean = goodsService.getPurchaseGoods(goods_idx);
        model.addAttribute("goods", tempGoodsBean);
        model.addAttribute("seller_id",sellerService.getSellerId(tempGoodsBean.getSeller_idx()));
        model.addAttribute("user_idx", loginUserBean.getUser_idx());

        return "redirect:/goods/goods_page";
    }

    //관심상품 조회
    @GetMapping("/user/user_wish")
    public String user_wish(Model model){
        model.addAttribute("wishBeanList", wishService.getUserWishList(loginUserBean.getUser_idx()));
        return "user/user_wish";
    }
}
