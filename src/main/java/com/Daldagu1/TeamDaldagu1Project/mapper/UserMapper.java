package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.AddrBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user_table (user_idx, user_id, user_pw, user_name, user_email, user_phone, user_role, user_available, user_daily, user_membership, user_birth)" +
            "values (user_seq.nextval, #{user_id}, #{user_pw}, #{user_name}, #{user_email}, #{user_phone}, 'D', 'T', 0, 0, #{user_birth})")
    void addUser(UserBean userBean);

    @Insert("insert into addr_table (addr_idx, user_idx, user_addr, user_post, addr_available, addr_name, addr_main, addr_phone)" +
            "values (addr_seq.nextval, #{user_idx}, #{user_addr}, #{user_post}, 'T', #{addr_name}, #{addr_main}, #{addr_phone} )")
    void addAddr(AddrBean addrBean);

    @Select("select user_idx from user_table where user_id=#{user_id}")
    int getUserIdx(String user_id);

    @Select("select user_id from user_table where user_id = #{user_id}")
    String getUserId(String user_id);

    @Select("select * from user_table where user_idx = #{user_idx}")
    UserBean getUserbyIdx(int user_idx);

    @Select("select * from user_table where user_id = #{user_id}")
    UserBean getUserbyId(String user_id);

    @Select("select seller_idx from seller_table where user_idx = #{user_idx}")
    int getSellerIdx(int user_idx);

    //배송지 호출
    @Select("select * from addr_table where user_idx=#{user_idx}")
    List<AddrBean> getExtraUserAddr(int user_idx);

    //배송지 수정
    @Update("update addr_table set user_addr=#{user_addr}, user_post=#{user_post}, addr_phone=#{addr_phone}, " +
            "addr_name=#{addr_name}, addr_main=#{addr_main} where user_idx=#{user_idx}")
    void getUpdateAddr(int user_idx);
}
