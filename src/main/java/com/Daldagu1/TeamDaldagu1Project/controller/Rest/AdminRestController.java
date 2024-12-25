package com.Daldagu1.TeamDaldagu1Project.controller.Rest;

import com.Daldagu1.TeamDaldagu1Project.beans.AddGoodsInfo;
import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SellerBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SellerInfoBean;
import com.Daldagu1.TeamDaldagu1Project.service.BannerService;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import com.Daldagu1.TeamDaldagu1Project.service.ReportService;
import com.Daldagu1.TeamDaldagu1Project.service.SellerService;
import com.Daldagu1.TeamDaldagu1Project.service.User.UserMyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminRestController {

    @Autowired
    private UserMyPageService userService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private ReportService reportService;

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

    //배너 승인
    @PostMapping("/rest/approve_banner")
    public void approve_banner(@RequestParam("banner_idx") int banner_idx) {
        bannerService.getBannerInfo(banner_idx);
    }

    //배너 반려
    @PostMapping("/rest/denial_banner")
    public void denial_banner(@RequestParam("banner_idx") int banner_idx) {
        bannerService.refusedBanner(banner_idx);
    }

    @PostMapping("/rest/delete_banner")
    public void delete_banner(@RequestParam("banner_idx") int banner_idx){
        bannerService.deleteBanner(banner_idx);
    }

    @PostMapping("/rest/remove_goods")
    public String remove(@RequestParam("Judge") boolean Judge, @RequestParam("goods_idx") int goods_idx){
        if(Judge) {
            goodsService.deleteGoods(goods_idx);
            return "Y";
        }
        else {
            reportService.removeReportCount(goods_idx);
            return "N";
        }
    }
}
