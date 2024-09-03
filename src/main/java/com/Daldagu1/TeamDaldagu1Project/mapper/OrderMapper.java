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

    //주문 리스트?
    @Select("select ot.order_idx, to_char(ot.order_date, 'YYYY-MM-DD') as order_date, gt.goods_name, gt.goods_price " +
            "from order_table ot, order_goods_table ogt, goods_table gt " +
            "where ot.order_idx = ogt.order_idx " +
            "and ogt.goods_idx = gt.goods_idx " +
            "and ot.user_idx= #{user_idx};")
    List<OrderGoodsBean> getOrderList(int user_idx);

}
