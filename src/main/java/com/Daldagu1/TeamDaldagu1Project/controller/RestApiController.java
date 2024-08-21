package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/rest/idCheck")
    public String IdCheck(@RequestParam("user_id")String user_id){
        String db_id = userService.getUserId(user_id);
        if(db_id == null) {
            return "true";
        }else{
            return "false";
        }
    }
}
