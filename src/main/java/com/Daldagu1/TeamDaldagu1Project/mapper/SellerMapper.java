package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.SellerBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SellerInfoBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SellerMapper {
    @Insert("insert into seller_table values(seller_seq.nextval, 0, #{calc_account}, #{selling_page_title}, #{user_idx})")
    void addSeller(SellerBean sellerBean);

    @Insert("insert into seller_join_info_table values (send_info_seq.nextval, #{seller_name}, #{seller_id}, #{seller_email}," +
            "#{seller_phone}, #{seller_page_title}, #{calc_account}, #{goods_tag},#{sample_img},#{user_idx})")
    void addSellerJoinInfo(SellerInfoBean sellerInfoBean);

    @Select("select * from seller_join_info_table where user_idx = #{user_idx}")
    SellerInfoBean getSellerInfo(int user_idx);

    @Select("select * from seller_join_info_table")
    List<SellerInfoBean> getSellerInfoList();


    @Update("update user_table set user_role = 'S' where user_idx = #{user_idx}")
    void updateUserRoll(int user_idx);

    @Delete("delete seller_join_info_table where user_idx = #{user_idx}")
    void deleteSellerJoinInfo(int user_idx);
}
