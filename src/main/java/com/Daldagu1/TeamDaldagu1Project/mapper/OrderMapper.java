package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.OrderBean;
import com.Daldagu1.TeamDaldagu1Project.beans.OrderGoodsBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    
    //주문 등록
    @Insert("insert into order_table value(order_seq.nextval, sysdate, #{order_delivery_addr}, #{order_delivery_num}, " +
            "#{user_idx}, #{order_goods_idx})")
    void addOrderForm(OrderBean orderBean);

    //주문 리스트
    @Select("select * from order_table where order_idx=#{order_idx}")
    List<OrderGoodsBean> getOrderList(int order_idx);



}
