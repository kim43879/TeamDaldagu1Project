package com.Daldagu1.TeamDaldagu1Project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class BoardController {
    @GetMapping("/board/board_list")
    public String boardList() {
        return "board/board_list";
    }
}
