package com.Daldagu1.TeamDaldagu1Project.controller.Rest;

import com.Daldagu1.TeamDaldagu1Project.beans.OptionBean;
import com.Daldagu1.TeamDaldagu1Project.beans.ReviewBean;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import com.Daldagu1.TeamDaldagu1Project.service.OptionService;
import com.Daldagu1.TeamDaldagu1Project.service.ReportService;
import com.Daldagu1.TeamDaldagu1Project.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class GoodsRestController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReportService reportService;

    //구매옵션 추가
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

    //옵션 제거
    @PostMapping(value = "/rest/goods/delete_option", produces = "text/plain; charset=UTF-8")
    public String delete_option(@RequestParam("option_idx") int option_idx){
        optionService.deleteOption(option_idx);
        return "옵션을 삭제했습니다.";
    }

    //상품 삭제
    @PostMapping("/rest/goods/delete")
    public void deleteGoods(@RequestParam("goods_idx") int goods_idx){
        goodsService.deleteGoods(goods_idx);
    }

    //리뷰등록
    @PostMapping("/rest/addReview")
    public void add_review(@RequestParam("review_title") String review_title,
                           @RequestParam("review_content")String review_content,
                           @RequestParam("user_id")int user_id,
                           @RequestParam("review_score") int review_score,
                           @RequestParam("goods_idx") int goods_idx,
                           @RequestParam(value = "review_file", required = false) MultipartFile review_file){

        ReviewBean reviewBean = new ReviewBean();
        reviewBean.setReview_title(review_title);
        reviewBean.setReview_content(review_content);
        reviewBean.setReview_score(review_score);
        reviewBean.setGoods_idx(goods_idx);
        reviewBean.setUser_idx(user_id);
        reviewBean.setReview_file(review_file);

        reviewService.addReview(reviewBean);
    }

    //신고카운트
    @PostMapping("/rest/report")
    public void addDeclarationCount(@RequestParam("goods_idx") int goods_idx){
        reportService.addReportCount(goods_idx);
    }
}
