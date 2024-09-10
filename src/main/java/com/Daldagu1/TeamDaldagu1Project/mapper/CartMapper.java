package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.CartBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    //물품 추가
    @Insert("insert into cart_table(cart_idx, goods_idx, user_idx, goods_quantity, selected_option, cart_price) " +
            "values(cart_seq.nextval, #{goods_idx}, #{user_idx}, #{goods_quantity}, #{selected_option}, #{cart_price})")
    void addUserCart(CartBean addCartBean);

    //물품 호출
    @Select("select c.*, g.goods_img as goods_img from cart_table c,goods_table g where c.goods_idx = g.goods_idx and user_idx = #{user_idx}")
    List<CartBean> getCartList(int user_idx);

    //물품 확인
    @Select("select c.cart_idx, c.goods_idx, c.user_idx, c.goods_quantity, c.selected_option " +
            "from cart_table c " +
            "join goods_table g on c.goods_idx = g.goods_idx " +
            "where c.user_idx = #{user_idx} and g.goods_name = #{goods_name} " +
            "and c.selected_option = #{selected_option}")
    CartBean cartInfo(@Param("user_idx") int user_idx, @Param("goods_idx") int goods_idx, @Param("selected_option") String selected_option, @Param("goods_name") String goods_name);

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

}
