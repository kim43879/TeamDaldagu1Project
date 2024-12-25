package com.Daldagu1.TeamDaldagu1Project.service.Order;

import com.Daldagu1.TeamDaldagu1Project.beans.OrderBean;
import com.Daldagu1.TeamDaldagu1Project.beans.OrderGoodsBean;
import com.Daldagu1.TeamDaldagu1Project.beans.PageBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.OrderMapper;
import jakarta.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderListService {
    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    @Autowired
    OrderMapper orderMapper;

    //주문 리스트
    public List<OrderBean> getOrderList( int user_idx, int page, String type, Integer orderStat) {

        // 기본 설정
        int itemsPerPage = 0;
        int start = 0;

        if(type.equals("USER") || type.equals("ORDER_STAT")) {
            itemsPerPage = 10;
            start = (page - 1) * itemsPerPage;
        }else if(type.equals("SELLER")){
            itemsPerPage = 5;
            start = (page - 1) * itemsPerPage;
        }
        RowBounds rowBounds = new RowBounds(start, itemsPerPage);

        // 데이터 조회
        List<OrderBean> list = new ArrayList<>();
        switch (type) {
            case "SELLER":
                list = orderMapper.getOrderListBySellerPage(user_idx, rowBounds);
                break;
            case "USER":
                list = page != -999 ? orderMapper.getOrderListByUserPage(user_idx, rowBounds) : orderMapper.getOrderListByUser(user_idx);
                break;
            case "ORDER_STAT":
                list = orderMapper.getOrderListByOrderStat(user_idx, orderStat, rowBounds);
                break;
            default: return null;
        }

        // 조회 후 데이터 처리
        for (OrderBean orderBean : list) {
            int quantity = 0;
            int price = 0;

            List<OrderGoodsBean> goodsList = orderMapper.getOrderGoodsList(orderBean.getOrder_idx());
            if (!goodsList.isEmpty()) {
                orderBean.setSample_img(goodsList.get(0).getImg());
                orderBean.setGoods_name(goodsList.get(0).getGoods_name());
                orderBean.setGoods_idx(goodsList.get(0).getGoods_idx());
            }

            for (OrderGoodsBean goodsBean : goodsList) {
                quantity += goodsBean.getOrder_goods_num();
                price += goodsBean.getPrice();
            }

            orderBean.setQuantity(quantity);
            orderBean.setOrder_price(price - orderBean.getUsed_point());
            orderBean.setOrder_statText(setOrderStatMessage(orderBean.getOrder_stat()));
        }
        return list;
    }

    //주문 상품 리스트
    public List<OrderGoodsBean> getOrderGoodsList(String order_idx) {
        System.out.println(orderMapper.getOrderGoodsList(order_idx).size());
        return orderMapper.getOrderGoodsList(order_idx);
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

    //판매자 페이징
    public PageBean getOrderCountForSeller(int seller_idx, int current_page){
        System.out.println(orderMapper.getOrderListBySeller(seller_idx).size());
        int order_count = orderMapper.getOrderListBySeller(seller_idx).size();
        PageBean pageBean = new PageBean(order_count, current_page, 5, 10);
        return pageBean;
    }

    //구매자 페이징
    public PageBean getOrderCountForUser(int user_idx, int current_page, Integer OrderStat){
        int order_count;
        if(OrderStat == null)
            order_count = orderMapper.getOrderListByUser(user_idx).size();
        else
            switch (OrderStat){
                case 1:
                    order_count = orderMapper.getOrderListByOrderStatCount(loginUserBean.getUser_idx(), 1).size();
                    break;
                case 2:
                    order_count = orderMapper.getOrderListByOrderStatCount(loginUserBean.getUser_idx(), 2).size();
                    break;
                case 3:
                    order_count = orderMapper.getOrderListByOrderStatCount(loginUserBean.getUser_idx(), 3).size();
                    break;
                case 4:
                    order_count = orderMapper.getOrderListByOrderStatCount(loginUserBean.getUser_idx(), 4).size();
                    break;
                default: order_count = 0;
            }


        PageBean pageBean = new PageBean(order_count, current_page, 10, 10);
        return pageBean;
    }
}
