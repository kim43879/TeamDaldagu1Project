package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into user_table (user_idx,user_id,user_pw) values (#{user_idx}, #{user_id}, #{user_pw})")
    void addUser(UserBean userBean);
}
