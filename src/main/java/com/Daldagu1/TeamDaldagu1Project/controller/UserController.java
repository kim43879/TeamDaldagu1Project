package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.mapper.UserMapper;
import com.Daldagu1.TeamDaldagu1Project.service.*;
import com.Daldagu1.TeamDaldagu1Project.validator.UserValidator;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

    @Autowired
    ReviewService reviewService;

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
                loginUserBean.login(tempUserBean);
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
        loginUserBean.clearUserBean();
        System.out.println(loginUserBean.getUser_id());
        return "redirect:/";
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

            System.out.println(userBean.getUser_birth());

            return "user/join";
        }
        userService.addUser(userBean);
        return "user/status/join_success";
    }

    @GetMapping("/user/user_page")
    public String user_page(Model model){
        List<OrderBean> orderList = orderService.getOrderListByUser(loginUserBean.getUser_idx());

        model.addAttribute("userBean", loginUserBean);
        model.addAttribute("wishBeanList", wishService.getUserWishList(loginUserBean.getUser_idx()));
        model.addAttribute("orderList", orderList);
        model.addAttribute("order_before_pay_cnt", orderService.getOrderCount(1, loginUserBean.getUser_idx()));
        model.addAttribute("orderCount", orderList.size());
        model.addAttribute("cartCount", cartService.getCartCnt(loginUserBean.getUser_idx()));
        model.addAttribute("reviewCnt", reviewService.getReviewCountForUser(loginUserBean.getUser_idx()));
        return "user/user_page";
    }

    //
    @GetMapping("/user/user_order")
    public String user_order(Model model){
        model.addAttribute("orderList", orderService.getOrderListByUser(loginUserBean.getUser_idx()));
        model.addAttribute("order_before_pay_cnt", orderService.getOrderCount(1, loginUserBean.getUser_idx()));
        model.addAttribute("order_ready_cnt", orderService.getOrderCount(2, loginUserBean.getUser_idx()));
        model.addAttribute("order_going_cnt", orderService.getOrderCount(3, loginUserBean.getUser_idx()));
        model.addAttribute("order_complete_cnt",orderService.getOrderCount(4, loginUserBean.getUser_idx()));
        return "user/user_order";
    }

    @GetMapping("/user/user_review")
    public String user_review(@RequestParam(name = "page", defaultValue = "1") int page, Model model){

        model.addAttribute("reviewList", reviewService.getReviewListForUser(loginUserBean.getUser_idx()));
        model.addAttribute("pageBean", reviewService.getReviewCountForUser(loginUserBean.getUser_idx(), page));

        return "user/user_review";
    }

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

    //관심상품
    @GetMapping("/user/status/wish_pro")
    public String user_wish(@RequestParam("goods_idx") int goods_idx, @RequestParam("user_idx") int user_idx,@RequestParam("result") boolean result, Model model) {
        wishService.addUserWish(goods_idx,user_idx);
        if(result) {
            List<WishBean> wishBeanList = wishService.getUserWishList(user_idx);
            model.addAttribute("wishBeanList", wishBeanList);
            return "user/user_wish";
        }else{
            GoodsBean tempGoodsBean = goodsService.getPurchaseGoods(goods_idx);
            model.addAttribute("goods", tempGoodsBean);
            model.addAttribute("goods_list", goodsService.getGoodsListByTag(tempGoodsBean.getGoods_tag()));
            model.addAttribute("seller_id", sellerService.getSellerbyUserIdx(tempGoodsBean.getSeller_idx()));
            model.addAttribute("user_idx",loginUserBean.getUser_idx());
            return "goods/goods_page";
        }
    }
    @GetMapping("/user/user_wish")
    public String user_wish(Model model){
        model.addAttribute("wishBeanList", wishService.getUserWishList(loginUserBean.getUser_idx()));
        return "user/user_wish";
    }

    @GetMapping("/user/user_pay")
    public String user_pay(){
        return "user/user_pay";
    }

    @GetMapping("/user/user_point")
    public String user_point(Model model){

        model.addAttribute("loginUserBean", loginUserBean);
        model.addAttribute("orderList", orderService.getOrderListByUser(loginUserBean.getUser_idx()));

        return "user/user_point";
    }

    @ModelAttribute("searchBean")
    public SearchBean getSearchBean() {
        return new SearchBean();
    }

    @GetMapping("/user/user_info")
    public String user_info() {
        return "user/user_info";
    }

    ///////////// - 문태일 수정
    //user_modify , user_modify_pro , initBinder 삭제

    // - 문태일 수정
    @PostMapping("/user/user_info_pro")
    public String user_info_pro(HttpServletRequest request, Model model) {

        String user_pw = request.getParameter("user_pw");
        ;
        System.out.println(user_pw);

        if (userService.checkPassword(user_pw)) {

            //UserBean modifyUserBean = userService.getModifyUserInfo(modifyUserBean);
            UserBean modifyUserBean = userService.getModifyUserBean();
            if (modifyUserBean != null) {
                System.out.println("유저 이름 : " + modifyUserBean.getUser_name());
            } else {
                System.out.println("null");
            }
            model.addAttribute("modifyUserBean", modifyUserBean);
            model.addAttribute("user_idx", loginUserBean.getUser_idx());
            System.out.println(modifyUserBean.getUser_idx());

            return "user/user_modify";
        } else {
            System.out.println("너 실패했어.");
            return "user/status/user_info_fail";
        }
    }
    // - 문태일 수정
    @GetMapping("/user/user_modify")
    private String user_modify(@ModelAttribute("modifyUserBean") UserBean modifyUserBean) { //수정된 회원의 값 주입

        userService.getModifyUserInfo(modifyUserBean);

        return "user/user_modify";
    }
    // - 문태일 수정
    @PostMapping("/user/update_password")
    public String updatePassword(@RequestParam("user_pw") String userPw,
                                 @RequestParam("user_pw2") String userPw2, UserBean modifyUserBean, Model model) {
        if (userPw == null || userPw2 == null || userPw.isEmpty() || !userPw.equals(userPw2)) {
            return "user/status/user_modify_fail_pw";
        }
        // 비밀번호 길이 검사
        if (userPw.length() < 8 || userPw.length() > 20 ||
                !userPw.matches(".*[A-Z].*") || !userPw.matches(".*\\d.*") ||
                !userPw.matches(".*[~!@#$%^&*()+|=].*")) {
            return "user/status/user_modify_fail_pw";
        }

        userService.modifyPassword(loginUserBean.getUser_idx(), userPw);
        return "user/status/user_modify_success";
    }
    // - 문태일 수정
    @PostMapping("/user/update_email")
    public String updateEmail(@RequestParam("user_email") String userEmail, UserBean modifyUserBean, Model model) {
        if (userEmail == null || userEmail.trim().isEmpty() ||
                !userEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            return "user/status/user_modify_fail_email";
        }

        userService.modifyEmail(loginUserBean.getUser_idx(), userEmail);
        return "user/status/user_modify_success";
    }
    // - 문태일 수정
    @PostMapping("/user/update_phone")
    public String updatePhone(@RequestParam("user_phone") String userPhone, UserBean modifyUserBean, Model model) {
        if (userPhone == null || userPhone.trim().isEmpty()) {
            return "user/status/user_modify_fail_phone";
        }
        if (userPhone.length() < 9 || userPhone.length() > 12 ||
                !userPhone.matches(".*[0-9].*")) {
            return "user/status/user_modify_fail_phone";

        }
        userService.modifyPhone(loginUserBean.getUser_idx(), userPhone);
        return "user/status/user_modify_success";
    }
}