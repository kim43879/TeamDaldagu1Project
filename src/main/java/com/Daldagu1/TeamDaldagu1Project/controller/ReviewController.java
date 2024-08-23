package com.Daldagu1.TeamDaldagu1Project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {

    @PostMapping("/review_write")
    public String reviewList() {
        return "/user/review_writer";
    }

}
