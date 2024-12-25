package com.Daldagu1.TeamDaldagu1Project.controller.Goods;

import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import com.Daldagu1.TeamDaldagu1Project.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/goods")
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

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

        model.addAttribute("searchGoodsList", searchService.searchGoodsList(searchBean, page));
        model.addAttribute("page", page);
        model.addAttribute("pageBean", searchService.getSearchPageCount(page,searchBean));
        model.addAttribute("totalGoodsNum", searchService.getTotalGoodsCnt(searchBean));

        System.out.println(searchService.getSearchPageCount(page,searchBean).getMin());
        System.out.println(searchService.getSearchPageCount(page,searchBean).getMax());

        if(searchBean.getSearchKeyword().equals("%")){searchBean.setSearchKeyword("");}
        else {searchBean.setSearchKeyword(keyword);}
        if(searchBean.getSearchCategory().equals("%")){searchBean.setSearchCategory("전체");}

        model.addAttribute("searchBean",searchBean);

        return "goods/search_page";
    }

    //페이지 이동시 받는 요청(Get)
    @GetMapping("/search_page/get")
    public String goodsSearch(@RequestParam("page") int page, @RequestParam("k") String k,
                              @RequestParam("c")String c, @RequestParam("min") int min
            , @RequestParam("max") int max, @RequestParam("s")String s,
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

        model.addAttribute("searchGoodsList", searchService.searchGoodsList(searchBean, page));
        model.addAttribute("page", page);
        model.addAttribute("pageBean", searchService.getSearchPageCount(page,searchBean));
        model.addAttribute("totalGoodsNum", searchService.getTotalGoodsCnt(searchBean));

        if(searchBean.getSearchKeyword().equals("%")){searchBean.setSearchKeyword("");}
        else {searchBean.setSearchKeyword(k);}
        if(searchBean.getSearchCategory().equals("%")){searchBean.setSearchCategory("전체");}

        model.addAttribute("searchBean",searchBean);

        return "goods/search_page";
    }
}
