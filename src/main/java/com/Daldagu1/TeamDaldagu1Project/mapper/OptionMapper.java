package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.OptionBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OptionMapper {
    @Insert("insert into option_table (option_idx, option_name, option_price, goods_idx) values(option_seq.nextval, #{option_name}, #{option_price}, #{goods_idx})")
    void addOption(OptionBean optionBean);

    @Select("select * from option_table where goods_idx = #{goods_idx}")
    List<OptionBean> getOptionList(int goods_idx);

    @Select("select option_available from option_table where option_idx= #{option_idx}")
    String getOptionAvailability(int option_idx);

    @Select("select count(*) from option_table where goods_idx = #{goods_idx}")
    int getOptionCount(int goods_idx);

    @Delete("delete from option_table where option_idx = #{option_idx}")
    void deleteOption(int option_idx);
}
