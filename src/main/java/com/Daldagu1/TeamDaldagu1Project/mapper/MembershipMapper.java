package com.Daldagu1.TeamDaldagu1Project.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MembershipMapper {
    @Select("select membership_grade from membership_table where membership_idx = #{membership_idx}")
    public String getMembership(int membership_idx);

    @Select("select membership_point from membership_table where membership_idx = #{membership_idx}")
    public float getMembershipPoint(int membership_idx);
}
