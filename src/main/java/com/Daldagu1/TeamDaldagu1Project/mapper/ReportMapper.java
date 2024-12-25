package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReportMapper {
    @Select("select goods_idx,goods_img,seller_idx, report_count from goods_table where report_count != 0 order by report_count desc")
    List<GoodsBean> getReportedGoodsList();

    @Update("update goods_table set report_count = report_count + 1 where goods_idx = #{goods_idx}")
    void addReportCount(int goods_idx);

    @Update("update goods_table set report_count = 0 where goods_idx = #{goods_idx}")
    void removeReportCount(int goods_idx);
}
