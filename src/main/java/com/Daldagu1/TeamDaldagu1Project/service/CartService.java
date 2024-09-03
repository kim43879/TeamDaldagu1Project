package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.CartBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    @Value("${page.paginationCnt}")
    private int paginationCnt;

    //장바구니 추가
    public void addUserCart(int user_idx, String goods_name, int goods_quantity, String selected_option) {

        CartBean cartStatus = cartMapper.cartInfo(user_idx, goods_name, selected_option);

        if(cartStatus == null) {
            CartBean userCartBean = new CartBean();
            userCartBean.setUser_idx(user_idx);
            userCartBean.setGoods_name(goods_name);
            userCartBean.setGoods_quantity(goods_quantity);
            userCartBean.setSelected_option(""); //option: String, 현재 내용이 없음

            cartMapper.addUserCart(userCartBean);

        } else {
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

    //장바구니 페이징
    public List<CartBean> getCartPage(int user_idx, int cartPage) {
        int offset = (cartPage - 1) * paginationCnt;
        return cartMapper.getCartPage(user_idx, cartPage, offset);
    }

}//class
