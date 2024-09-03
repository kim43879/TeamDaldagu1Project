package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.mapper.UserMapper;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import com.Daldagu1.TeamDaldagu1Project.service.SellerService;
import com.Daldagu1.TeamDaldagu1Project.service.UserService;
import com.Daldagu1.TeamDaldagu1Project.service.WishService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller()
public class UserController {

    @Resource(name = "loginUserBean")
    @Lazy
    private UserBean loginUserBean;

    @Autowired
    UserService userService;

    @Autowired
    WishService wishService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    SellerService sellerService;

    @GetMapping("/user/login")
    public String login(Model model, @RequestParam(name = "fail", defaultValue = "false") boolean fail){
        model.addAttribute("userBean", new UserBean());
        model.addAttribute("fail", fail);

        return "user/login";
    }

    @PostMapping("/user/login_pro")
    public String login_pro(@ModelAttribute("userBean") UserBean userBean){

        UserBean tempUserBean = userService.getUserbyId(userBean.getUser_id());

        if(tempUserBean != null){
            if(tempUserBean.getUser_pw().equals(userBean.getUser_pw())){
                loginUserBean.setUser_idx(tempUserBean.getUser_idx());
                loginUserBean.setLoginCheck(true);
                loginUserBean.setUser_name(tempUserBean.getUser_name());
                loginUserBean.setUser_role(tempUserBean.getUser_role());
                loginUserBean.setSeller_idx(tempUserBean.getSeller_idx());
                loginUserBean.setUser_profile_img(tempUserBean.getUser_profile_img());

                return "user/status/login_success";
            }else {

                return "user/status/login_fail";
            }
        }else {
            return "user/status/login_fail";
        }
    }

    @GetMapping("/user/logout")
    public String logout(){
        loginUserBean.setLoginCheck(false);
        loginUserBean.setUser_id(null);
        loginUserBean.setUser_name(null);
        loginUserBean.setUser_role(null);

        return "/";
    }

    @GetMapping("/user/join")
    public String join(Model model){
        model.addAttribute("userBean", new UserBean());
        return "user/join";
    }

    @PostMapping("/user/join_pro")
    public String join_pro(@Valid @ModelAttribute("userBean") UserBean userBean, BindingResult result,Model model){
        if(result.hasErrors()){
            model.addAttribute("userBean", userBean);
            for(ObjectError error : result.getAllErrors()){
                System.out.println(error.getCode());
            }

            return "user/join";
        }
        userService.addUser(userBean);
        return "user/status/join_success";
    }

    @GetMapping("/user/user_page")
    public String user_page(Model model){
        model.addAttribute("user_name", loginUserBean.getUser_name());
        return "user/user_page";
    }

    //
    @GetMapping("/user/user_order")
    public String user_order(HttpServletRequest request){

        return "user/user_order";
    }

    @GetMapping("/user/user_review")
    public String user_review(){
        return "user/user_review";
    }

    //관심상품등록
    @GetMapping("/user/add_user_wish")
    public String add_user_wish(@RequestParam("goods_idx") int goods_idx,
                            @RequestParam("user_idx") int user_idx,
                            @RequestParam("result") boolean result, Model model){

        wishService.addUserWish(goods_idx, user_idx);

        if(result) {
            List<WishBean> wishBeanList = wishService.getUserWishList(user_idx);
            model.addAttribute("wishBeanList", wishBeanList);

            return "user/user_wish";
        }
        GoodsBean tempGoodsBean = goodsService.getPurchaseGoods(goods_idx);
        model.addAttribute("goods", tempGoodsBean);
        model.addAttribute("seller_id",sellerService.getSellerId(tempGoodsBean.getSeller_idx()));
        model.addAttribute("user_idx", loginUserBean.getUser_idx());

        return "goods/goods_page";
    }

    //관심상품
    @GetMapping("/user/user_wish")
    public String user_wish(@RequestParam("user_idx") int user_idx, Model model) {

        List<WishBean> wishBeanList = wishService.getUserWishList(user_idx);
        model.addAttribute("wishBeanList", wishBeanList);

        return "user/user_wish";
    }

    @GetMapping("/user/user_info")
    public String user_info(){
        return "user/user_info";
    }

    @GetMapping("/user/user_pay")
    public String user_pay(){
        return "user/user_pay";
    }

    @ModelAttribute("searchBean")
    public SearchBean getSearchBean() {
        return new SearchBean();
    }
}
