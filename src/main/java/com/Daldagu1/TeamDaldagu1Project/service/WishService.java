package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.WishBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.WishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    @Autowired
    private WishMapper wishMapper;
    
    //관심상품 저장
    public void addUserWish(int user_idx, int goods_idx) {

        WishBean userWishBean = new WishBean();
        userWishBean.setGoods_idx(user_idx);
        userWishBean.setUser_idx(goods_idx);

        System.out.println(userWishBean.getUser_idx());
        System.out.println(userWishBean.getGoods_idx());

        wishMapper.addUserWish(userWishBean);
    }

    //관심상품 호출
    public List<WishBean> getUserWishList(int user_idx) {
        return wishMapper.getUserWishList(user_idx);
    }

    //상품 삭제
    public void deleteUserWish(int wish_idx) {
        wishMapper.deleteUserWish(wish_idx);
    }
}
