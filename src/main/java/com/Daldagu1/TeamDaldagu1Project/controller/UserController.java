package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.mapper.UserMapper;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import com.Daldagu1.TeamDaldagu1Project.service.SellerService;
import com.Daldagu1.TeamDaldagu1Project.service.UserService;
import com.Daldagu1.TeamDaldagu1Project.service.WishService;
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
//                loginUserBean.setUser_idx(tempUserBean.getUser_idx());
//                loginUserBean.setUser_id(tempUserBean.getUser_id());
//                loginUserBean.setLoginCheck(true);
//                loginUserBean.setUser_name(tempUserBean.getUser_name());
//                loginUserBean.setUser_role(tempUserBean.getUser_role());
//                loginUserBean.setSeller_idx(tempUserBean.getSeller_idx());
//                loginUserBean.setUser_profile_img(tempUserBean.getUser_profile_img());
//                loginUserBean.setUser_profile_text(tempUserBean.getUser_profile_text());
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

            System.out.println(userBean.getUser_birth());

            return "user/join";
        }
        userService.addUser(userBean);
        return "user/status/join_success";
    }

    @PostMapping("/user/modify")
    public String modify(@RequestParam("user_pw") String user_pw, Model model){
        System.out.println(user_pw);
        UserBean modifyUserBean = userService.getModifyUser(user_pw);

        if(modifyUserBean != null){
            //modifyUserBean.setUser_addr1("주소1");
            modifyUserBean.setId_check("체크");
            model.addAttribute("modifyUserBean", modifyUserBean);
            return "user/user_info2";
        }else{

            model.addAttribute("fail", "fail");
            return "user/user_info";
        }
    }

    @PostMapping("/user/modify_pro")
    public String modify_pro(@Valid @ModelAttribute("modifyUserBean") UserBean modifyUserBean, BindingResult result, Model model){

        System.out.println(modifyUserBean.getUser_pw());
        System.out.println(modifyUserBean.getUser_pw2());
        System.out.println(modifyUserBean.getUser_addr1());
        System.out.println(modifyUserBean.getUser_addr2());

        if(result.hasErrors()){

            for (FieldError error : result.getFieldErrors()) {
                System.out.println("Field: " + error.getField() + ", Error: " + error.getDefaultMessage());
            }
            //model.addAttribute("modifyUserBean", modifyUserBean);

            return "user/user_info2";
        }else {
            System.out.println("수정완료");
            userService.modifyUser(modifyUserBean);
            return "user/status/modify_success";
        }
    }

    @GetMapping("/user/user_page")
    public String user_page(Model model){

        model.addAttribute("user_name", loginUserBean.getUser_name());
        model.addAttribute("wishBeanList", wishService.getUserWishList(loginUserBean.getUser_idx()));

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

    @InitBinder("modifyUserBean")
    public void initBinder(WebDataBinder binder) {
        UserValidator uservalidator = new UserValidator();
        binder.addValidators(uservalidator);
    }
}
