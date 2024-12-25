package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.AdminBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {

    @Insert("insert admin_table into (admin_idx, admin_id, admin_pw, admin_task, notice_writer_idx) " +
            "values(admin_seq.nextval, #{admin_id}, #{admin_pw}, #{admin_task}, #{notice_writer_idx})")
    void addAdmin(AdminBean adminBean);

    @Select("select * from admin_table where admin_id = #{admin_id}")
    AdminBean getLoginAdminBean(AdminBean adminBean);

    @Select("select count(*) from user_table where user_available = 'T'")
    int getUserCnt();

    @Select("select count(*) from goods_table where goods_available = 'T'")
    int getGoodsCnt();

    @Select("select count(*) from order_table where to_char(order_date, 'YYYY-MM-DD') =  to_char(sysdate, 'YYYY-MM-DD')")
    int getOrderCnt();
}
