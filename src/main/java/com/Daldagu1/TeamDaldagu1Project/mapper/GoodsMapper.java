package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.AddGoodsInfo;
import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface GoodsMapper {
    @Insert("insert into goods_table(goods_idx, goods_name, goods_tag, goods_price, goods_img, goods_stock, goods_available,seller_idx, goods_img2, goods_text)" +
            "values (goods_seq.nextval, #{goods_name}, #{goods_tag}, #{goods_price}, " +
            "#{goods_img}, 0, 'T',#{seller_idx}, #{goods_img2}, #{goods_info})")
    void addGoodsInfo(GoodsBean addGoodsBean);

    @Insert("insert into add_goods_info_table(info_idx, goods_name, goods_tag, goods_price, goods_img, goods_info,seller_idx, goods_img2) " +
            "values (goods_info_seq.nextval, #{goods_name}, #{goods_tag}, #{goods_price}, #{goods_img},#{goods_info},#{seller_idx}, #{goods_img2})")
    void addAddGoodsInfo(AddGoodsInfo addGoodsInfoBean);

    //구매상품 호출
    @Select("select * from goods_table where goods_idx=#{goods_idx}")
    GoodsBean getPurchaseGoods(int goods_idx);

    //상품등록신청 목록 호출
    @Select("select * from add_goods_info_table")
    List<AddGoodsInfo> getAddGoodsList();

    @Select("select * from add_goods_info_table where info_idx = #{info_idx}")
    AddGoodsInfo getAddGoodsByIdx(int info_idx);

    //자신이 등록한 상품 목록 호출
    @Select("select * from goods_table where seller_idx=#{seller_idx}")
    List<GoodsBean> getMyGoodsList(int seller_idx);

    @Select("select * from goods_table where goods_tag=#{goods_tag}")
    List<GoodsBean> getGoodsListByTag(String goods_tag);
//  select * from (select * from goods_table where goods_name like '%' and goods_tag like '%' and goods_price > 0 and goods_price < 2147483647 order by goods_name) where rownum <= 8;
    @Select("select goods_idx, goods_name, goods_price, goods_img from goods_table where goods_name like #{searchKeyword} and goods_tag like #{searchCategory} and goods_price > #{searchMinPrice} and goods_price < #{searchMaxPrice} order by goods_name")
    List<GoodsBean> searchGoodsList(SearchBean searchBean, RowBounds rowBounds);
    @Select("select * from goods_table where goods_name like #{searchKeyword} and goods_tag like #{searchCategory} and goods_price > #{searchMinPrice} and goods_price < #{searchMaxPrice} order by goods_price")
    List<GoodsBean> searchGoodsListOrder_price1(SearchBean searchBean, RowBounds rowBounds);
    @Select("select * from goods_table where goods_name like #{searchKeyword} and goods_tag like #{searchCategory} and goods_price > #{searchMinPrice} and goods_price < #{searchMaxPrice} order by goods_price desc")
    List<GoodsBean> searchGoodsListOrder_price2(SearchBean searchBean, RowBounds rowBounds);

    @Select("select count(*) from goods_table where goods_name like #{searchKeyword} and goods_tag like #{searchCategory} and goods_price > #{searchMinPrice} and goods_price < #{searchMaxPrice} order by goods_name")
    int searchGoodsListCnt(SearchBean searchBean);
    @Select("select count(*) from goods_table where goods_name like #{searchKeyword} and goods_tag like #{searchCategory} and goods_price > #{searchMinPrice} and goods_price < #{searchMaxPrice} order by goods_price")
    int searchGoodsListOrder_priceCnt(SearchBean searchBean);
    @Select("select count(*) from goods_table where goods_name like #{searchKeyword} and goods_tag like #{searchCategory} and goods_price > #{searchMinPrice} and goods_price < #{searchMaxPrice} order by goods_price desc")
    int searchGoodsListOrder_price2Cnt(SearchBean searchBean);

    @Select("select count(*) from goods_table where seller_idx = #{seller_idx}")
    int goodsCountBySellerIdx(int seller_idx);

    @Delete("delete from add_goods_info_table where info_idx = #{info_idx}")
    void deleteAddGoods(int info_idx);
}
