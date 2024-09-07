package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import com.Daldagu1.TeamDaldagu1Project.service.OptionService;
import com.Daldagu1.TeamDaldagu1Project.service.ReviewService;
import com.Daldagu1.TeamDaldagu1Project.service.SellerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/goods")
@Controller
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    SellerService sellerService;

    @Autowired
    OptionService optionService;

    @Autowired
    ReviewService reviewService;

    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    @GetMapping("/goods_page")
    public String goods_page(@RequestParam("goods_idx") int goods_idx,
                             @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        GoodsBean tempGoodsBean = goodsService.getPurchaseGoods(goods_idx);

        //상품페이지에 리뷰목록 등록
        List<ReviewBean> reviewList = reviewService.getReviewList(goods_idx, page);
        PageBean pageBean = reviewService.getReviewCount(goods_idx, page);

        model.addAttribute("goods", tempGoodsBean);
        model.addAttribute("seller_id",sellerService.getSellerId(tempGoodsBean.getSeller_idx()));
        model.addAttribute("goods_list", goodsService.getGoodsListByTag(tempGoodsBean.getGoods_tag()));



        model.addAttribute("reviewList", reviewList);
        model.addAttribute("pageBean", pageBean);

        model.addAttribute("user_idx", loginUserBean.getUser_idx());
        model.addAttribute("option_list", optionService.getOptionList(goods_idx));

        return "goods/goods_page";
    }

    @GetMapping("/goods_buy")
    public String goodsBuy() {
        return "goods/goods_buy";
    }
    @GetMapping("/goods_seller")
    public String goodsSeller() {
        return "goods/goods_seller";
    }

    //새로운 검색 시 사용되는 요청(Post)
    @PostMapping("/search_page")
    public String goodsSearch(@ModelAttribute("searchBean") SearchBean searchBean, Model model,
                              @RequestParam(value = "page", defaultValue = "1") int page) {

        System.out.println("post 검색 요청 발생");

        String keyword = searchBean.getSearchKeyword();

        if(searchBean.getSearchKeyword().isEmpty()) {
            searchBean.setSearchKeyword("%");
        }else{
            searchBean.setSearchKeyword("%" + searchBean.getSearchKeyword() + "%");
        }

        if(searchBean.getSearchCategory().equals("전체")){
            searchBean.setSearchCategory("%");
        }

        model.addAttribute("searchGoodsList", goodsService.searchGoodsList(searchBean, page));
        model.addAttribute("page", page);
        model.addAttribute("pageBean", goodsService.getSearchPageCount(page,searchBean));
        model.addAttribute("totalGoodsNum", goodsService.getTotalGoodsCnt(searchBean));

        System.out.println(goodsService.getSearchPageCount(page,searchBean).getMin());
        System.out.println(goodsService.getSearchPageCount(page,searchBean).getMax());

        if(searchBean.getSearchKeyword().equals("%")){searchBean.setSearchKeyword("");}
        else {searchBean.setSearchKeyword(keyword);}
        if(searchBean.getSearchCategory().equals("%")){searchBean.setSearchCategory("전체");}

        model.addAttribute("searchBean",searchBean);

        return "goods/search_page";
    }
    //페이지 이동시 받는 요청(Get)
    @GetMapping("/search_page/get")
    public String goodsSearch(@RequestParam("page") int page,@RequestParam("k") String k,
                               @RequestParam("c")String c,@RequestParam("min") int min
                                ,@RequestParam("max") int max,@RequestParam("s")String s,
                                @RequestParam("sc") int sc, Model model) {
        System.out.println("get 검색 요청 발생");

        SearchBean searchBean = new SearchBean();
        searchBean.setSearchKeyword(k);
        searchBean.setSearchCategory(c);
        searchBean.setSearchMinPrice(min);
        searchBean.setSearchMaxPrice(max);
        searchBean.setSortType(s);
        searchBean.setShowCount(sc);

        if(searchBean.getSearchKeyword().isEmpty()) {
            searchBean.setSearchKeyword("%");
        }else{
            searchBean.setSearchKeyword("%" + searchBean.getSearchKeyword() + "%");
        }

        if(searchBean.getSearchCategory().equals("전체")){
            searchBean.setSearchCategory("%");
        }

        model.addAttribute("searchGoodsList", goodsService.searchGoodsList(searchBean, page));
        model.addAttribute("page", page);
        model.addAttribute("pageBean", goodsService.getSearchPageCount(page,searchBean));
        model.addAttribute("totalGoodsNum", goodsService.getTotalGoodsCnt(searchBean));

        System.out.println(goodsService.getSearchPageCount(page,searchBean).getMin());
        System.out.println(goodsService.getSearchPageCount(page,searchBean).getMax());

        if(searchBean.getSearchKeyword().equals("%")){searchBean.setSearchKeyword("");}
        else {searchBean.setSearchKeyword(k);}
        if(searchBean.getSearchCategory().equals("%")){searchBean.setSearchCategory("전체");}

        model.addAttribute("searchBean",searchBean);

        return "goods/search_page";
    }

    @PostMapping("/add_goods_pro")
    public String addGoodsPro(@ModelAttribute("addGoodsInfoBean")AddGoodsInfo addGoodsInfo, Model model){
        goodsService.addGoodsInfoApply(addGoodsInfo);
        return "redirect:/seller/seller_product_insert";
    }

    @ModelAttribute("searchBean")
    public SearchBean getSearchBean() {
        return new SearchBean();
    }
}
