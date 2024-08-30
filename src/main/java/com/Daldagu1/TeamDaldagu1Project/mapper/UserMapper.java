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
    @Insert("insert into user_table (user_idx, user_id, user_pw, user_name, user_email, user_phone, user_role, user_available, user_daily, membership_idx, user_birth)" +
            "values (user_seq.nextval, #{user_id}, #{user_pw}, #{user_name}, #{user_email}, #{user_phone}, 'D', 'T', 0, 0, #{user_birth})")
    void addUser(UserBean userBean);

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


}
