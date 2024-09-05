package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.CartBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    @Value("${page.paginationCnt}")
    private int paginationCnt;

    //장바구니 추가
    public void addUserCart(int user_idx, int goods_idx, int goods_quantity, String selected_option, String goods_name, int option_price) {

        CartBean cartStatus = cartMapper.cartInfo(user_idx, goods_idx, selected_option, goods_name);

        if(cartStatus == null) {
            CartBean userCartBean = new CartBean();
            System.out.println("카트 서비스 : " + user_idx);

            userCartBean.setUser_idx(user_idx);
            userCartBean.setGoods_idx(goods_idx);
            userCartBean.setGoods_quantity(goods_quantity);
            userCartBean.setSelected_option(selected_option);
            userCartBean.setCart_price(option_price);

            cartMapper.addUserCart(userCartBean);

        } else { //장바구니 업데이트?
            System.out.println("카트 : " + cartStatus.getCart_idx());
            System.out.println("카트 : " + cartStatus.getUser_idx());
            System.out.println("카트 : " + cartStatus.getGoods_idx());
            System.out.println("카트 : " + cartStatus.getSelected_option());

            int modifyQuantity = cartStatus.getGoods_quantity() + goods_quantity;
            cartMapper.updateCartQuantity(cartStatus.getCart_idx(), modifyQuantity);
        }
    }

    //장바구니 호출
    public List<CartBean> getCartList(int user_idx) {
        return cartMapper.getCartList(user_idx);
    }

    //장바구니 옵션
    public void updateCartOption(int cart_idx, String selected_option) {
        cartMapper.updateCartOption(cart_idx, selected_option);
    }

    //장바구니 수량 업데이트
    public void updateCartQuantity(int cart_idx, int goods_quantity) {
        cartMapper.updateCartQuantity(cart_idx, goods_quantity);
    }

    //장바구니 삭제
    public void removeUserCart(int cart_idx) {
        cartMapper.removeUserCart(cart_idx);
    }

    //전체 상품 수량
    public int getCartCnt(int user_idx) {
        return cartMapper.getCartCnt(user_idx);
    }

    //장바구니 총액
    public int getTotalPrice(int user_idx) {
        return cartMapper.getTotalPrice(user_idx);
    }

}//class
