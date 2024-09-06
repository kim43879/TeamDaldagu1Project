package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.OrderBean;
import com.Daldagu1.TeamDaldagu1Project.beans.OrderGoodsBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    
    //주문 추가
    public void addOrderForm(OrderBean orderBean) {
        orderMapper.addOrderForm(orderBean);
    }

    public void addOrderGoods(OrderGoodsBean orderGoodsBean){
        orderMapper.addOrderGoods(orderGoodsBean);
    }

    //주문 리스트
    public List<OrderGoodsBean> getOrderGoodsList(String order_idx) {
        System.out.println(orderMapper.getOrderGoodsList(order_idx).size());
        return orderMapper.getOrderGoodsList(order_idx);
    }

    public String getOrder_idx(){
        LocalDate now = LocalDate.now();
        String uuid = UUID.randomUUID().toString();
        String year = String.valueOf(now.getYear());
        String month = String.valueOf(now.getMonthValue());
        if (now.getMonthValue() < 10){
            month = "0" + month;
        }
        String day = String.valueOf(now.getDayOfMonth());
        if(now.getDayOfMonth() < 10){
            day = "0" + day;
        }

        String order_idx = year + month + day + "-" + uuid.substring(0,8);

        return order_idx;
    }

    public OrderBean getOrder(String order_idx){
        return orderMapper.getOrder(order_idx);
    }

    public void orderPaymentSuccess(String order_idx){
        orderMapper.orderPaymentSuccess(order_idx);
    }

    public void setOrderMessage(String message, String order_idx){
        orderMapper.setOrderMessage(message, order_idx);
    }

    public void deleteOrder(String order_idx){
        orderMapper.deleteOrderGoods(order_idx);
        orderMapper.deleteOrder(order_idx);
    }

}
