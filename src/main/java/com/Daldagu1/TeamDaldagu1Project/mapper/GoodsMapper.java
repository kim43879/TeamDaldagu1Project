package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.AddGoodsInfo;
import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsMapper {
    @Insert("insert into goods_table(goods_idx, goods_name, goods_tag, goods_price, goods_img, goods_stock, goods_available,seller_idx)" +
            "values (goods_seq.nextval, #{goods_name}, #{goods_tag}, #{goods_price}, " +
            "#{goods_img}, 0, 'T',#{seller_idx})")
    void addGoodsInfo(GoodsBean addGoodsBean);

    @Insert("insert into add_goods_info_table(info_idx, goods_name, goods_tag, goods_price, goods_img, goods_info,seller_idx) " +
            "values (goods_info_seq.nextval, #{goods_name}, #{goods_tag}, #{goods_price}, #{goods_img},#{goods_info},#{seller_idx})")
    void addAddGoodsInfo(AddGoodsInfo addGoodsInfoBean);

    //구매상품 호출
    @Select("select * from goods_table where goods_id=#{goods_idx}")
    List<GoodsBean> getPurchaseGoods(int goods_idx);

    //상품등록신청 목록 호출
    @Select("select * from add_goods_info_table")
    List<AddGoodsInfo> getAddGoodsList();

    @Select("select * from add_goods_info_table where info_idx = #{info_idx}")
    AddGoodsInfo getAddGoodsByIdx(int info_idx);


    @Delete("delete from add_goods_info_table where info_idx = #{info_idx}")
    void deleteAddGoods(int info_idx);
}
