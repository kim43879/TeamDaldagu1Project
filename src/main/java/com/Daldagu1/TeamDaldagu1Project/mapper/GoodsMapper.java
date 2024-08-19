package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper {
    @Insert("insert into goods_table(goods_idx, goods_name, goods_tag, goods_price, goods_img, goods_stock, goods_available)" +
            "values(goods_seq.nextval, #{goods_name}, #{goods_tag}, #{goods_price}, " +
            "#{goods_img, jdbcType=VARCHAR}, #{goods_stock}, #{goods_available}")
    void addGoodsInfo(GoodsBean addGoodsBean);
}
