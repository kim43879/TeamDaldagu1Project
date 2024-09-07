package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.NoticeBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SellerInfoBean;
import com.Daldagu1.TeamDaldagu1Project.service.GoodsService;
import com.Daldagu1.TeamDaldagu1Project.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private GoodsService goodsService;

    //private NoticeBean noticeBean;

    //관리자 메인페이지
    @GetMapping("/dev_admin_main")
    String mainAdminPage() {
        return "admin_page";
    }

    //판매자 등록
    @GetMapping("/dev_admin_page")
    public String devAdminPage(Model model) {
        List<SellerInfoBean> sellerInfoList = sellerService.getSellerInfoList();

        model.addAttribute("sellerInfoList",sellerInfoList);

        return "admin/dev_admin_page";
    }

    //상품 승인
    @GetMapping("/dev_admin_goods_page")
    public String devAdminGoodsPage(Model model) {

        model.addAttribute("addGoodsInfoList", goodsService.getAddGoodsInfoList());

        return "admin/dev_admin_goods_page";
    }

    //공지사항 목록
    @GetMapping("/notice_admin_list")
    public String notice_admin(Model model) {
//        List<NoticeBean> noticeList = new ArrayList<>();
//
//        noticeList.add(new NoticeBean(1, "공지사항", "관리자_ED", "2024-09-06", 1));
//        noticeList.add(new NoticeBean(2, "공지사항", "관리자_ED", "2024-09-06", 1));
//        noticeList.add(new NoticeBean(3, "공지사항", "관리자_ED", "2024-09-06", 1));
//        noticeList.add(new NoticeBean(4, "공지사항", "관리자_ED", "2024-09-06", 1));
//        noticeList.add(new NoticeBean(5, "공지사항", "관리자_ED", "2024-09-06", 1));
//        noticeList.add(new NoticeBean(6, "공지사항", "관리자_ED", "2024-09-06", 1));
//        noticeList.add(new NoticeBean(7, "공지사항", "관리자_ED", "2024-09-06", 1));
//        noticeList.add(new NoticeBean(8, "공지사항", "관리자_ED", "2024-09-06", 1));
//        noticeList.add(new NoticeBean(9, "공지사항", "관리자_ED", "2024-09-06", 1));
//        noticeList.add(new NoticeBean(10, "공지사항", "관리자_ED", "2024-09-06", 1));
//
//        model.addAttribute("noticeList", noticeList);

        return "admin/notice_admin_list";
    }

    //공지작성
    @GetMapping("/notice_write")
    public String notice_write() {
        return "admin/notice_write";
    }

}//class
