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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class AddrController {           //주소 컨트롤러

    @Autowired
    private AddrService addrService;

    @Resource(name = "loginUserBean")
    @Lazy
    private UserBean loginUserBean;

    @GetMapping("/user_addr")
    public String user_addr(@RequestParam("user_idx") int user_idx, Model model){       //

        List<AddrBean> testAddr = addrService.getExtraUserAddr(user_idx);
        model.addAttribute("testAddr", testAddr);

        for(AddrBean bean : testAddr){
            System.out.println("주소 : " + bean.getUser_addr());
        }

        return "user/user_addr";
    }

    @GetMapping("/add_user_addr")
    public String add_user_addr(Model model){
        AddrBean addrBean = new AddrBean();
        addrBean.setUser_idx(loginUserBean.getUser_idx());

        model.addAttribute("addrBean", addrBean);

        return "user/add_user_addr";
    }

    @PostMapping("/add_user_addr/add")
    public String add_user_addr_add(@ModelAttribute("addrBean") AddrBean addrBean, Model model){
        String message = addrService.addAddr(addrBean);
        model.addAttribute("message", message);

        return "user/status/add_addr_success";
    }

    @GetMapping("/update_user_addr")
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

    @PostMapping("/update_user_addr/update")
    public String update_user_addr_update(@ModelAttribute("addrBean") AddrBean addrBean, @RequestParam("addr_idx") int addr_idx,Model model){



        if(addrBean.getAddr_main() == null)
            addrBean.setAddr_main("F");
        else
            addrService.unMainAddr(addrBean.getUser_idx());

        addrBean.showField();

        addrService.updateAddr(addrBean);

        model.addAttribute("message","배송지 정보 수정이 완료되었습니다.");

        return "user/status/add_addr_success";
    }
}
