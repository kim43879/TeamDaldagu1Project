package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.OrderBean;
import com.Daldagu1.TeamDaldagu1Project.beans.OrderGoodsBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
    public List<OrderBean> getOrderListBySeller(int seller_idx) {
        List<OrderBean> list = orderMapper.getOrderListBySeller(seller_idx);
        for (OrderBean orderBean : list){
            int quantity = 0;
            int price = 0;

            List<OrderGoodsBean> goodsList = orderMapper.getOrderGoodsList(orderBean.getOrder_idx());
            orderBean.setSample_img(goodsList.get(0).getImg());
            for(OrderGoodsBean goodsBean : goodsList){
                quantity += goodsBean.getOrder_goods_num();
                price += goodsBean.getPrice();
            }
            orderBean.setQuantity(quantity);
            orderBean.setOrder_price(price);
            orderBean.setOrder_statText(setOrderStatMessage(orderBean.getOrder_stat()));
        }
        return list;
    }

    public List<OrderBean> getOrderListByUser(int user_idx){
        List<OrderBean> list = orderMapper.getOrderListByUser(user_idx);
        for(OrderBean orderBean : list){
            int quantity = 0;
            int price = 0;

            List<OrderGoodsBean> goodsList = orderMapper.getOrderGoodsList(orderBean.getOrder_idx());
            orderBean.setGoods_name(goodsList.get(0).getGoods_name());
            orderBean.setGoods_idx(goodsList.get(0).getGoods_idx());
            for(OrderGoodsBean goodsBean : goodsList){
                quantity += goodsBean.getOrder_goods_num();
                price += goodsBean.getPrice();
            }
            orderBean.setQuantity(quantity);
            orderBean.setOrder_price(price);
            orderBean.setOrder_statText(setOrderStatMessage(orderBean.getOrder_stat()));

            System.out.println("OrderIdx : " + orderBean.getOrder_idx());
        }
        return list;
    }

    //주문 물품 리스트
    public List<OrderGoodsBean> getOrderGoodsList(String order_idx) {
        System.out.println(orderMapper.getOrderGoodsList(order_idx).size());
        return orderMapper.getOrderGoodsList(order_idx);
    }

    public List<OrderBean> getOrderListByOrderStat(int user_idx, int order_stat){
        List<OrderBean> list = orderMapper.getOrderListByOrderStat(user_idx, order_stat);
        for(OrderBean orderBean : list){
            int quantity = 0;
            int price = 0;

            List<OrderGoodsBean> goodsList = orderMapper.getOrderGoodsList(orderBean.getOrder_idx());
            orderBean.setGoods_name(goodsList.get(0).getGoods_name());
            orderBean.setGoods_idx(goodsList.get(0).getGoods_idx());
            for(OrderGoodsBean goodsBean : goodsList){
                quantity += goodsBean.getOrder_goods_num();
                price += goodsBean.getPrice();
            }
            orderBean.setQuantity(quantity);
            orderBean.setOrder_price(price);
            orderBean.setOrder_statText(setOrderStatMessage(orderBean.getOrder_stat()));

            System.out.println("OrderIdx : " + orderBean.getOrder_idx());
        }
        return list;
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

    public int getOrderCount(int order_stat, int user_idx){
        return orderMapper.getOrderCount(order_stat, user_idx);
    }

    public void deleteOrder(String order_idx){
        orderMapper.deleteOrderGoods(order_idx);
        orderMapper.deleteOrder(order_idx);
    }

    public String setOrderStatMessage(int order_stat){
        String orderStatText = "";
        switch(order_stat){
            case 2:
                orderStatText = "배송 준비중";
                break;
            case 3:
                orderStatText = "배송 중";
                break;
            case 4:
                orderStatText = "배송 완료";
                break;
            case 5:
                orderStatText = "취소";
                break;
            case 6:
                orderStatText = "교환";
                break;
            case 7:
                orderStatText = "반품";
                break;
            case 8:
                orderStatText = "환불";
                break;
            default:
                orderStatText = "오류";
        }
        return orderStatText;
    }

    public void nextOrderProcess(String order_idx){
        int currentProcess = orderMapper.getOrderStat(order_idx);
        currentProcess += 1;
        orderMapper.nextOrderProcess(currentProcess, order_idx);
    }
}
