package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.SellerBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SellerInfoBean;
import com.Daldagu1.TeamDaldagu1Project.service.SellerService;
import com.Daldagu1.TeamDaldagu1Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private SellerService sellerService;

    @PostMapping("/rest/idCheck")
    public String IdCheck(@RequestParam("user_id")String user_id){
        String db_id = userService.getUserId(user_id);
        if(db_id == null) {
            return "true";
        }else{
            return "false";
        }
    }

    @PostMapping("/rest/approve_seller")
    public void approveSeller(@RequestParam("user_idx") int user_idx){
        if(userService.getUserbyIdx(user_idx).getUser_role().equals("S")){}
        else {
            SellerInfoBean tempSellerInfoBean = sellerService.getSellerInfo(user_idx);

            SellerBean tempSellerBean = new SellerBean();
            tempSellerBean.setCalc_account(tempSellerInfoBean.getCalc_account());
            tempSellerBean.setSelling_page_title(tempSellerInfoBean.getSeller_page_title());
            tempSellerBean.setUser_idx(user_idx);

            sellerService.addSeller(tempSellerBean);
            sellerService.deleteSellerJoinInfo(tempSellerBean.getUser_idx());
        }
    }

    @PostMapping("/rest/denial_seller")
    public void denialSeller(@RequestParam("user_idx") int user_idx){
        sellerService.deleteSellerJoinInfo(user_idx);
    }
}
