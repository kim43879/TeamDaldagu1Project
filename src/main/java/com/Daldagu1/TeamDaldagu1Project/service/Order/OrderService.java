package com.Daldagu1.TeamDaldagu1Project.service.Order;

import com.Daldagu1.TeamDaldagu1Project.beans.OrderBean;
import com.Daldagu1.TeamDaldagu1Project.beans.OrderGoodsBean;
import com.Daldagu1.TeamDaldagu1Project.beans.PageBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.GoodsMapper;
import com.Daldagu1.TeamDaldagu1Project.mapper.OrderMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodsMapper goodsMapper;
    
    //주문 추가
    public void addOrderForm(OrderBean orderBean) {
        orderMapper.addOrderForm(orderBean);
    }

    //주문 상품 추가
    public void addOrderGoods(OrderGoodsBean orderGoodsBean){
        orderMapper.addOrderGoods(orderGoodsBean);
    }

    //주문번호 생성
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

    public void orderPaymentSuccess(String order_idx, int add_point, int used_point, int quantity){
        int goods_idx = orderMapper.getOrderGoodsList(order_idx).get(0).getGoods_idx();

        System.out.println("정보 업데이트");
        System.out.println("주문 수량 " + quantity);

        orderMapper.orderPaymentSuccess(order_idx,add_point, used_point);
        goodsMapper.updateGoodsStock(quantity, goods_idx);
    }

    public void deleteOrder(String order_idx){
        orderMapper.deleteOrderGoods(order_idx);
        orderMapper.deleteOrder(order_idx);
    }

    public void setOrderMessage(String message, String order_idx){
        orderMapper.setOrderMessage(message, order_idx);
    }

    public void nextOrderProcess(String order_idx){
        int currentProcess = orderMapper.getOrderStat(order_idx);
        currentProcess += 1;
        orderMapper.nextOrderProcess(currentProcess, order_idx);
    }


}
