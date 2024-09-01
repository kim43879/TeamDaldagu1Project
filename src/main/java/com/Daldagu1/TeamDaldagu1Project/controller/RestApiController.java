package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.service.AddrService;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import com.Daldagu1.TeamDaldagu1Project.service.SellerService;
import com.Daldagu1.TeamDaldagu1Project.service.UserService;
import com.Daldagu1.TeamDaldagu1Project.service.WishService;
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

}
