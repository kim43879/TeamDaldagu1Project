package com.Daldagu1.TeamDaldagu1Project.controller.Rest;

import com.Daldagu1.TeamDaldagu1Project.beans.AddrBean;
import com.Daldagu1.TeamDaldagu1Project.service.AddrService;
import com.Daldagu1.TeamDaldagu1Project.service.CartService;
import com.Daldagu1.TeamDaldagu1Project.service.User.UserMyPageService;
import com.Daldagu1.TeamDaldagu1Project.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MyPageRestController {

    @Autowired
    private UserMyPageService userService;

    @Autowired
    private AddrService addrService;

    @Autowired
    private WishService wishService;

    @Autowired
    private CartService cartService;

    //주소 삭제
    @PostMapping(value = "/rest/delete_addr", produces = "text/plain; charset=UTF-8")
    public String delete_addr(@RequestParam("addr_idx") int addr_idx){

        AddrBean tempAddrBean = addrService.getAddrByAddrIdx(addr_idx);

        if(tempAddrBean.getAddr_main().equals("F")){
            addrService.deleteAddr(addr_idx);
            return "삭제했습니다.";
        }
        return "기본 주소지는 삭제할 수 없습니다.";
    }

    //관심상품 삭제
    @PostMapping("/rest/delete_wish")
    public void delete_wish(@RequestParam("wish_idx") int wish_idx) {
        wishService.deleteUserWish(wish_idx);
    }

    //장바구니 추가
    @PostMapping("/rest/add_cart")
    public void addCart(@RequestParam("user_idx") int user_idx,
                        @RequestParam("goods_idx") int goods_idx,
                        @RequestParam("goods_quantity") int goods_quantity,
                        @RequestParam("selected_option") String selected_option,
                        @RequestParam("result_price") int result_price,
                        @RequestParam("goods_name") String goods_name) {
        System.out.println("컨트롤러 user_idx : " + user_idx);
        cartService.addUserCart(user_idx, goods_idx, goods_quantity, selected_option, goods_name, result_price);
    }

    //장바구니 삭제
    @PostMapping("/rest/remove_cart")
    public void remove_cart(@RequestParam("cart_idx") int cart_idx) {
        cartService.removeUserCart(cart_idx);
    }

    //프로필 이미지 변경
    @PostMapping("/rest/profile_modify")
    public void profile_modify(@RequestParam("userIdx") int user_idx,
                               @RequestParam("profile") MultipartFile profile){
        System.out.println(profile.getName());
        userService.updateProfile(user_idx, profile);
    }

}
