package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.OrderBean;
import com.Daldagu1.TeamDaldagu1Project.beans.OrderGoodsBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    
    //주문 추가
    public void addOrderForm(OrderBean orderBean) {
        orderMapper.addOrderForm(orderBean);
    }

    //주문 리스트
    public List<OrderGoodsBean> getOrderList(int order_idx) {
        return orderMapper.getOrderList(order_idx);
    }

}
