package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.CartBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    //물품 추가
    @Insert("insert into cart_table(cart_idx, goods_idx, user_idx, goods_quantity, selected_option) " +
            "values(cart_seq.nextval, #{goods_idx}, #{user_idx}, #{goods_quantity}, #{selected_option})")
    void addUserCart(CartBean addCartBean);

    //물품 호출
    @Select("select * from cart_table where user_idx= #{user_idx}")
    List<CartBean> getCartList(int user_idx);

    //물품 확인
    @Select("select & from cart_table where user_idx= #{user_idx} and goods_idx= #{goods_idx}, " +
            "and selected_option= #{selected_option}")
    CartBean cartInfo(int user_idx, int goods_idx, String selected_option);

    //물품 삭제
    @Delete("delete from cart_table where cart_idx= #{cart_idx}")
    void removeUserCart(int cart_idx);
    
    //옵션 변경
    @Update("update cart_table set selected_option= #{selected_option} where cart_idx= #{cart_idx}")
    void updateCartOption(int cart_idx, String selected_option);

    //수량 변경
    @Update("update cart_table set goods_quantity= #{goods_quantity} where cart_idx= #{cart_idx}")
    void updateCartQuantity(int cart_idx, int goods_quantity);

    //물품 수량
    @Select("select count(*) from cart_table where user_idx= #{user_idx}")
    int getCartCnt(int user_idx);

    //옵션 합산 금액
    @Select("select nvl(sum((g.goods_price + 2000) * c.goods_quantity), -1) as calc_price " +
            "from cart_table c, goods_table g " +
            "where c.goods_idx = g.goods_idx " +
            "and c.user_idx= #{user_idx} ")
    int getTotalPrice(int user_idx);

    //페이징
    @Select("select * from cart_table where user_idx " +
            "order by cart_idx desc")
    List<CartBean> getCartPage(int user_idx, int cartPage, int offset);

}
