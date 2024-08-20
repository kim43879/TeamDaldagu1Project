package com.Daldagu1.TeamDaldagu1Project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/board")
@Controller
public class BoardController {
    @GetMapping("/board_list")
    public String boardList() {
        return "board/board_list";
    }
}

