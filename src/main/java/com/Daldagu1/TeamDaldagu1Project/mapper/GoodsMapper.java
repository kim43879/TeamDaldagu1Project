package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsMapper {
    @Insert("insert into goods_table(goods_idx, goods_name, goods_tag, goods_price, goods_img, goods_stock, goods_available)" +
            "values (goods_seq.nextval, #{goods_name}, #{goods_tag}, #{goods_price}, " +
            "#{goods_img, jdbcType=VARCHAR}, #{goods_stock}, #{goods_available}")
    void addGoodsInfo(GoodsBean addGoodsBean);

    //구매상품 호출
    @Select("select * from goods_table where goods_id=#{goods_idx}")
    List<GoodsBean> getPurchaseGoods(int goods_idx);

}
