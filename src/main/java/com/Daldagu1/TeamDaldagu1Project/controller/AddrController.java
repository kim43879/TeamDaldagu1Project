package com.Daldagu1.TeamDaldagu1Project.controller;

import com.Daldagu1.TeamDaldagu1Project.beans.AddrBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.service.AddrService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AddrController {

    @Autowired
    private AddrService addrService;

    @Resource(name = "loginUserBean")
    @Lazy
    private UserBean loginUserBean;

    @GetMapping("/user/user_addr")
    public String user_addr(@RequestParam("user_idx") int user_idx, Model model){

        List<AddrBean> testAddr = addrService.getExtraUserAddr(user_idx);
        model.addAttribute("testAddr", testAddr);

        for(AddrBean bean : testAddr){
            System.out.println("주소 : " + bean.getUser_addr());
        }

        return "user/user_addr";
    }

    @GetMapping("/user/add_user_addr")
    public String add_user_addr(Model model){
        AddrBean addrBean = new AddrBean();
        addrBean.setUser_idx(loginUserBean.getUser_idx());

        model.addAttribute("addrBean", addrBean);

        return "user/add_user_addr";
    }

    @PostMapping("/user/add_user_addr/add")
    public String add_user_addr_add(@ModelAttribute("addrBean") AddrBean addrBean){
        addrService.addAddr(addrBean);

        return "user/status/add_addr_success";
    }

    @GetMapping("/user/update_user_addr")
    public String update_user_addr(@RequestParam("addr_idx") int addr_idx, Model model){
        AddrBean addrBean = addrService.getAddrByAddrIdx(addr_idx);
        String[] addr = addrBean.getUser_addr().split(",");
        String addr1 = addr[0];
        String addr2 = addr[1].substring(1);
        addrBean.setUser_addr1(addr1);
        addrBean.setUser_addr2(addr2);
        model.addAttribute("addrBean", addrBean);
        return "user/update_user_addr";
    }

    @PostMapping("/user/update_user_addr/update")
    public String update_user_addr_update(@ModelAttribute("addrBean") AddrBean addrBean, @RequestParam("addr_idx") int addr_idx){

        addrBean.showField();

        if(addrBean.getAddr_main().isEmpty())
            addrBean.setAddr_main("F");
        else
            addrService.unMainAddr(addrBean.getUser_idx());
        addrService.updateAddr(addrBean);
        return "user/status/add_addr_success";
    }

    @ModelAttribute("searchBean")
    public SearchBean getSearchBean() {
        return new SearchBean();
    }

}