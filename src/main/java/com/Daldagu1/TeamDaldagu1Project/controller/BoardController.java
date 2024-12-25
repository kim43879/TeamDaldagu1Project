package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import com.Daldagu1.TeamDaldagu1Project.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/board")
@Controller
public class BoardController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/notice_normal")
    public String notice_user(Model model) {
        model.addAttribute("noticeList", noticeService.getNoticeList());

        return "board/notice_normal";
    }

    @GetMapping("/notice_read")
    public String notice_user_read(@RequestParam("notice_idx") int notice_idx, Model model) {
        model.addAttribute("notice", noticeService.getNotice(notice_idx));

        return "board/notice_read";
    }
}//class

