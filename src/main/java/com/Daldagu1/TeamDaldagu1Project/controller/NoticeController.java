package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.AdminBean;
import com.Daldagu1.TeamDaldagu1Project.beans.NoticeBean;
import com.Daldagu1.TeamDaldagu1Project.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin")
@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private AdminBean adminBean;

    //공지사항 목록
    @GetMapping("/notice_admin_list")
    public String notice_admin(Model model) {
        model.addAttribute("noticeList", noticeService.getNoticeList());

        return "admin/notice_admin_list";
    }

    //공지작성
    @GetMapping("/notice_write")
    public String notice_write(@ModelAttribute("noticeBean") NoticeBean addNoticeInfoBean) {

        return "admin/notice_write";
    }
    @PostMapping("/notice_write_pro")
    public String notice_write_pro(@ModelAttribute("noticeBean") NoticeBean addNoticeInfoBean) {
        noticeService.addNotice(addNoticeInfoBean);

        return "redirect:/admin/notice_admin_list";
    }

    //공지확인
    @GetMapping("/notice_admin_read")
    public String notice_admin_read(@RequestParam("notice_idx") int notice_idx, Model model) {
        model.addAttribute("noticeBean", noticeService.getNotice(notice_idx));

        return "admin/notice_admin_read";
    }

    //공지수정
    @GetMapping("/notice_modify")
    public String notice_modify(@RequestParam("notice_idx") int notice_idx, Model model) {
        NoticeBean modifyNotice = noticeService.getNotice(notice_idx);
        model.addAttribute("noticeBean", modifyNotice);

        return "admin/notice_modify";
    }
    @PostMapping("/notice_modify_pro")
    public String notice_modify_pro(@ModelAttribute NoticeBean modifyNoticeBean) {
        noticeService.modifyNotice(modifyNoticeBean);

        return "redirect:/admin/notice_admin_list";
    }

    //공지삭제
    @GetMapping("/notice_delete")
    public String notice_delete(@RequestParam("notice_idx") int notice_idx) {
        noticeService.deleteNotice(notice_idx);

        return "redirect:/admin/notice_admin_list";
    }

}//class
