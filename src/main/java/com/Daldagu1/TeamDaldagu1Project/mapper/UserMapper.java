package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.AddrBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user_table (user_idx, user_id, user_pw, user_name, user_email, user_phone, user_role, user_available, user_daily, membership_idx, user_birth,user_profile_img, user_profile_text)" +
            "values (user_seq.nextval, #{user_id}, #{user_pw}, #{user_name}, #{user_email}, #{user_phone}, 'D', 'T', 0, 0, #{user_birth}, 'default_profile.png', '메세지가 아직 없습니다.')")
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

    @Select("select user_name, user_id, user_email, user_birth " +
            "from user_table where user_id = #{user_id} and user_pw = #{user_pw}")
    UserBean getModifyUser(@Param("user_id") String user_id, @Param("user_pw") String user_pw);

    @Update("update user_table set " +
            "user_pw = #{user_pw}," +
            "user_email = #{user_email}, " +
            "user_phone = #{user_phone} " +
            "where user_id = #{user_id}" )
    void modifyUser(UserBean modifyUserBean);
}
