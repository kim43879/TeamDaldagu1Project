package com.Daldagu1.TeamDaldagu1Project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MainPageMapper {

    @Select("select goods_table.goods_tag, count(*) as tag_count " +
            "from goods_table " +
            "left join order_goods_table " +
            "on order_goods_table.goods_idx = goods_table.goods_idx " +
            "group by goods_table.goods_tag " +
            "order by tag_count desc")
    public String[] getMainTagsByNormal();

    @Select("SELECT g.goods_tag, COALESCE(SUM(og.order_goods_num), 0) AS tag_count " +
            "FROM goods_table g " +
            "JOIN order_goods_table og ON g.goods_idx = og.goods_idx " +
            "JOIN order_table o ON og.order_idx = o.order_idx " +
            "WHERE o.user_idx = #{user_idx} " +
            "GROUP BY g.goods_tag " +
            "ORDER BY tag_count DESC")
    public String[] getMainTagsByUser(Integer uesr_idx);
}
