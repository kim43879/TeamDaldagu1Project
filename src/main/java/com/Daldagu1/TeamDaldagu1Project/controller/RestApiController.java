package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private AddrService addrService;

    @Autowired
    private WishService wishService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OptionService optionService;

    @PostMapping("/rest/idCheck")
    public String IdCheck(@RequestParam("user_id")String user_id){
        String db_id = userService.getUserId(user_id);
        if(db_id == null) {
            return "true";
        }else{
            return "false";
        }
    }

    //판매자 승인
    @PostMapping("/rest/approve_seller")
    public void approveSeller(@RequestParam("user_idx") int user_idx){
        if(userService.getUserbyIdx(user_idx).getUser_role().equals("S")){}
        else {
            SellerInfoBean tempSellerInfoBean = sellerService.getSellerInfo(user_idx);

            SellerBean tempSellerBean = new SellerBean();
            tempSellerBean.setCalc_account(tempSellerInfoBean.getCalc_account());
            tempSellerBean.setSelling_page_title(tempSellerInfoBean.getSeller_page_title());
            tempSellerBean.setUser_idx(user_idx);

            sellerService.addSeller(tempSellerBean);
            sellerService.deleteSellerJoinInfo(tempSellerBean.getUser_idx());
        }
    }

    //판매자 반려
    @PostMapping("/rest/denial_seller")
    public void denialSeller(@RequestParam("user_idx") int user_idx){
        sellerService.deleteSellerJoinInfo(user_idx);
    }

    //승인
    @PostMapping("/rest/approve_goods")
    public void approve_goods(@RequestParam("info_idx") int info_idx){
        AddGoodsInfo tempGoodsInfoBean = goodsService.getAddGoodsInfo(info_idx);
        GoodsBean tempGoodsBean = new GoodsBean();

        tempGoodsBean.setGoods_name(tempGoodsInfoBean.getGoods_name());
        tempGoodsBean.setGoods_tag(tempGoodsInfoBean.getGoods_tag());
        tempGoodsBean.setGoods_price(tempGoodsInfoBean.getGoods_price());
        tempGoodsBean.setGoods_img(tempGoodsInfoBean.getGoods_img());
        tempGoodsBean.setSeller_idx(tempGoodsInfoBean.getSeller_idx());
        tempGoodsBean.setGoods_img2(tempGoodsInfoBean.getGoods_img2());
        tempGoodsBean.setGoods_text(tempGoodsInfoBean.getGoods_info());

        goodsService.addGoodsInfo(tempGoodsBean);
        goodsService.deleteAddGoodsInfo(info_idx);
    }

    //굿즈 반려
    @PostMapping("/rest/denial_goods")
    public void denial_goods(@RequestParam("info_idx") int info_idx){
        goodsService.deleteAddGoodsInfo(info_idx);
    }

    //찜 삭제
    @PostMapping("/rest/delete_wish")
    public void delete_wish(@RequestParam("wish_idx") int wish_idx) {
        wishService.deleteUserWish(wish_idx);
    }

    //장바구니 추가
    @PostMapping("/rest/add_cart")
    public void addCart(@RequestParam("user_idx") int user_idx,
                        @RequestParam("goods_idx") int goods_idx,
                        @RequestParam("goods_quantity") int goods_quantity,
                        @RequestParam("selected_option") String selected_option,
                        @RequestParam("result_price") int result_price,
                        @RequestParam("goods_name") String goods_name) {
        System.out.println("컨트롤러 user_idx : " + user_idx);
        cartService.addUserCart(user_idx, goods_idx, goods_quantity, selected_option, goods_name, result_price);
    }

    //장바구니 삭제
    @PostMapping("/rest/remove_cart")
    public void remove_cart(@RequestParam("cart_idx") int cart_idx) {
        cartService.removeUserCart(cart_idx);
    }

    //주소 삭제
    @PostMapping(value = "/rest/delete_addr", produces = "text/plain; charset=UTF-8")
    public String delete_addr(@RequestParam("addr_idx") int addr_idx){

        AddrBean tempAddrBean = addrService.getAddrByAddrIdx(addr_idx);

        if(tempAddrBean.getAddr_main().equals("F")){
            addrService.deleteAddr(addr_idx);
            return "삭제했습니다.";
        }
        return "기본 주소지는 삭제할 수 없습니다.";
    }

    @PostMapping(value = "/rest/goods/add_option", produces = "text/plain; charset=UTF-8")
    public String add_option(@RequestParam("option_name") String option_name,
                             @RequestParam("option_price") int option_price,
                             @RequestParam("goods_idx") int goods_idx){

        OptionBean optionBean = new OptionBean();
        optionBean.setOption_name(option_name);
        optionBean.setOption_price(option_price);
        optionBean.setGoods_idx(goods_idx);

        if(optionService.getOptionCount(goods_idx) < 5) {
            optionService.addOption(optionBean);
            return "옵션을 추가했습니다.";
        }else{
            return "옵션은 5개까지 등록할 수 있습니다.";
        }
    }
    @PostMapping(value = "/rest/goods/delete_option", produces = "text/plain; charset=UTF-8")
    public String delete_option(@RequestParam("option_idx") int option_idx){
         optionService.deleteOption(option_idx);
         return "옵션을 삭제했습니다.";
    }

}//class
