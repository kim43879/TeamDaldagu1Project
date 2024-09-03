package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.OptionBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OptionMapper {
    @Insert("insert from option_table (option_idx, option_name, option_price, goods_idx) values(option_seq.nextval(), #{option_name}, #{option_price}, #{goods_idx})")
    void addOption(OptionBean optionBean);

    @Select("select * from option_table where goods_idx = #{goods_idx}")
    List<OptionBean> getOptionList(int goods_idx);
}
