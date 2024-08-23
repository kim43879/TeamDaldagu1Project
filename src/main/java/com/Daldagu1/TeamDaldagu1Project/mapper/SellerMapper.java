package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.SellerBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SellerInfoBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SellerMapper {
    //판매자 추가
    @Insert("insert into seller_table (seller_idx, sell_calc_num, sell_calc_account, selling_page_title, user_idx)" +
            " values(seller_seq.nextval, 0, #{calc_account}, #{selling_page_title}, #{user_idx})")
    void addSeller(SellerBean sellerBean);

    //판매자 등록 대기목록 추가
    @Insert("insert into seller_join_info_table (info_idx, seller_name, seller_id, seller_email, seller_phone, seller_page_title, calc_account, goods_tag, sample_img, user_idx)" +
            "values (send_info_seq.nextval, #{seller_name}, #{seller_id}, #{seller_email}," +
            "#{seller_phone}, #{seller_page_title}, #{calc_account}, #{goods_tag},#{sample_img},#{user_idx})")
    void addSellerJoinInfo(SellerInfoBean sellerInfoBean);

    //판매자 정보 조회(user_idx로 검색 삭제 가능성 존재)
    @Select("select * from seller_join_info_table where user_idx = #{user_idx}")
    SellerInfoBean getSellerInfo(int user_idx);

    //판매자 등록 대기목록 가져오기(어드민에서 사용)
    @Select("select * from seller_join_info_table")
    List<SellerInfoBean> getSellerInfoList();

    //
    @Select("select * from seller_table where seller_idx = #{seller_idx}")
    SellerBean getSellerByIdx(int seller_idx);


    @Update("update user_table set user_role = 'S' where user_idx = #{user_idx}")
    void updateUserRoll(int user_idx);

    @Delete("delete seller_join_info_table where user_idx = #{user_idx}")
    void deleteSellerJoinInfo(int user_idx);
}
