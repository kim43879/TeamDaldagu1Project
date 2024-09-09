package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.OrderBean;
import com.Daldagu1.TeamDaldagu1Project.beans.OrderGoodsBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    
    //주문 등록
    @Insert("insert into order_table (order_idx, addr_idx, user_idx, seller_idx, order_stat) " +
            "values(#{order_idx}, #{addr_idx}, #{user_idx}, #{seller_idx}, 1)")
    void addOrderForm(OrderBean orderBean);

    @Insert("insert into order_goods_table (order_goods_idx, order_goods_num, price, selected_option,goods_idx,order_idx) " +
            "values(order_goods_seq.nextval, #{order_goods_num}, #{price}, #{selected_option}, #{goods_idx}, #{order_idx})")
    void addOrderGoods(OrderGoodsBean orderGoodsBean);

    @Select("select count(*) from order_table where to_char(order_date, 'YYYY-MM-DD') =  to_char(sysdate, 'YYYY-MM-DD') and seller_idx = ${seller_idx}")
    int getTodayOrderCount(@Param("seller_idx") int seller_idx);

    @Select("select * from order_table where seller_idx = #{seller_idx} and order_stat > 1 order by order_date desc")
    List<OrderBean> getOrderListBySeller(int seller_idx);

    @Select("select * from order_table where user_idx = #{user_idx} and order_stat > 1")
    List<OrderBean> getOrderListByUser(int user_idx);

    @Select("select * from order_table where user_idx = #{user_idx} and ORDER_STAT = #{order_stat}")
    List<OrderBean> getOrderListByOrderStat(@Param("user_idx") int user_idx, @Param("order_stat") int order_stat);

    @Select("select o.order_goods_idx as order_goods, " +
            "o.order_goods_num as order_goods_num, " +
            "o.goods_idx as goods_idx, " +
            "o.order_idx as order_idx, o.price as price, " +
            "o.selected_option as selected_option, g.goods_img as img, " +
            "g.goods_name as goods_name " +
            "from order_goods_table o, goods_table g " +
            "where o.goods_idx = g.goods_idx and o.order_idx = #{order_idx}")
    List<OrderGoodsBean> getOrderGoodsList(String order_idx);

    @Select("select order_idx, TO_CHAR(order_date, 'YYYY-MM-DD HH24:MI:SS') as order_date, " +
            "addr_idx, user_idx, order_message, seller_idx " +
            "from order_table " +
            "where order_idx=#{order_idx}")
    OrderBean getOrder(String order_idx);

    @Select("select count(*) from order_table where ORDER_STAT = #{order_stat} and user_idx = #{user_idx}")
    int getOrderCount(@Param("order_stat") int order_stat, @Param("user_idx") int user_idx);

    @Select("select order_stat from order_table where order_idx = #{order_idx}")
    int getOrderStat(String order_idx);

    @Update("update order_table set order_stat = #{order_stat} where order_idx = #{order_idx}")
    void nextOrderProcess(@Param("order_stat") int order_stat, @Param("order_idx") String order_idx);

    @Update("update order_table set order_date = sysdate, order_stat = 2 where order_idx = #{order_idx}")
    void orderPaymentSuccess(String order_idx);

    @Update("update order_table set order_message = #{message} where order_idx = #{order_idx}")
    void setOrderMessage(@Param("message") String message,@Param("order_idx") String order_idx);

    @Delete("delete from order_goods_table where order_idx = #{order_idx}")
    void deleteOrderGoods(String order_idx);

    @Delete("delete from order_table where order_idx = #{order_idx}")
    void deleteOrder(String order_idx);

}
