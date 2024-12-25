package com.Daldagu1.TeamDaldagu1Project.service.Order;

import com.Daldagu1.TeamDaldagu1Project.beans.OrderBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCountService {

    @Autowired
    private OrderMapper orderMapper;

    public int getTodayOrderCount(int seller_idx){
        return orderMapper.getTodayOrderCount(seller_idx);
    }


    public int getOrderCount(int order_stat, int user_idx){
        return orderMapper.getOrderCount(order_stat, user_idx);
    }

    public int getOrderCnt(int seller_idx){
        return orderMapper.getOrderCnt(seller_idx);
    }
}
