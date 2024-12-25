package com.Daldagu1.TeamDaldagu1Project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MembershipMapper {
    @Select("select membership_grade from membership_table where membership_idx = #{membership_idx}")
    public String getMembership(int membership_idx);

    @Select("select membership_point from membership_table where membership_idx = #{membership_idx}")
    public float getMembershipPoint(int membership_idx);

    @Select("SELECT COALESCE(SUM(og.price), 0) " +
            "FROM order_goods_table og " +
            "JOIN order_table o ON og.order_idx = o.order_idx " +
            "WHERE o.user_idx = #{user_idx} " +
            "AND o.order_date >= TRUNC(SYSDATE, 'MONTH') " +
            "AND o.order_date < SYSDATE"
    )
    int getUsedMoneyByMonth(int user_idx);

    @Update("EXEC update_user_grades")
    void updateUserMemberships();
}
