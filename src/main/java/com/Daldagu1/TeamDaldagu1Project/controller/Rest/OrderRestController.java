package com.Daldagu1.TeamDaldagu1Project.controller.Rest;

import com.Daldagu1.TeamDaldagu1Project.beans.OrderBean;
import com.Daldagu1.TeamDaldagu1Project.beans.OrderGoodsBean;
import com.Daldagu1.TeamDaldagu1Project.service.AddrService;
import com.Daldagu1.TeamDaldagu1Project.service.Order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddrService addrService;

    //주문 추가
    @PostMapping("/rest/add_order")
    public String add_order(@RequestParam("user_idx") int user_idx, @RequestParam("seller_idx") int seller_idx){
        int addr_idx = addrService.getMainAddrIdx(user_idx);

        System.out.println(user_idx);
        System.out.println(seller_idx);

        String order_idx = orderService.getOrder_idx();
        OrderBean tempOrderBean = new OrderBean();
        tempOrderBean.setOrder_idx(order_idx);
        tempOrderBean.setUser_idx(user_idx);
        tempOrderBean.setAddr_idx(addr_idx);
        tempOrderBean.setSeller_idx(seller_idx);
        tempOrderBean.setOrder_stat(1);

        orderService.addOrderForm(tempOrderBean);

        return order_idx;
    }

    //주문 상품추가
    @PostMapping("/rest/add_order_goods")
    public void add_order_goods(@RequestParam("goods_idx") int goods_idx,
                                @RequestParam("goods_quantity") int goods_quantity,
                                @RequestParam("selected_option") String selected_option,
                                @RequestParam("result_price") int result_price,
                                @RequestParam("order_idx") String order_idx){
        OrderGoodsBean tempOrderGoogsBean = new OrderGoodsBean();
        tempOrderGoogsBean.setOrder_idx(order_idx);
        tempOrderGoogsBean.setGoods_idx(goods_idx);
        tempOrderGoogsBean.setPrice(result_price);
        tempOrderGoogsBean.setOrder_goods_num(goods_quantity);
        tempOrderGoogsBean.setSelected_option(selected_option);

        orderService.addOrderGoods(tempOrderGoogsBean);

        System.out.println("구매요청");
    }

    //주문 삭제
    @PostMapping("/rest/order/order_delete")
    public void order_delete(@RequestParam("order_idx") String order_idx){
        orderService.deleteOrder(order_idx);
    }

    //주문 상태 진행
    @PostMapping("/rest/next_order_process")
    public void next_order_process(@RequestParam("order_idx") String order_idx){
        orderService.nextOrderProcess(order_idx);
    }
}
