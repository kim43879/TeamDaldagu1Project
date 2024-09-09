package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.AddrBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    //회원가입
    @Insert("insert into user_table (user_idx, user_id, user_pw, user_name, user_email, user_phone, user_role, user_available, user_daily, membership_idx, user_birth,user_profile_img, user_profile_text)" +
            "values (user_seq.nextval, #{user_id}, #{user_pw}, #{user_name}, #{user_email}, #{user_phone}, 'D', 'T', 0, 1, #{user_birth}, 'default_profile.png', '메세지가 아직 없습니다.')")
    void addUser(UserBean userBean);

    //id로 idx 가져오기
    @Select("select user_idx from user_table where user_id=#{user_id}")
    int getUserIdx(String user_id);

    //id중복체크 mapper
    @Select("select user_id from user_table where user_id = #{user_id}")
    String getUserId(String user_id);

    //idx로 유저객체 반환
    @Select("select * from user_table where user_idx = #{user_idx}")
    UserBean getUserbyIdx(int user_idx);

    // id로 유저객체 반환(로그인)
    @Select("select * from user_table where user_id = #{user_id} and user_available = 'T'")
    UserBean getUserbyId(String user_id);

    @Select("select seller_idx from seller_table where user_idx = #{user_idx}")
    int getSellerIdx(int user_idx);

    /////////////문태일 수정
    /// getModifyUser , modifyUser 삭제
    //DB에서 수정할 회원의 정보를 가져오기 - 문태일 수정
    @Select("select user_id, user_name from user_table where user_idx = #{user_idx}")
    UserBean getModifyUserInfo(int user_idx); //매개변수로 user_idx(회원의 기본키)를 보냄

    //회원정보 수정(DB에 수정된 정보 업데이트)- 문태일 수정
    @Update("update user_table set user_pw = #{user_pw}, user_email = #{user_email}, user_phone = #{user_phone} where user_idx = #{user_idx}")
    void modifyUserInfo(UserBean modifyUserBean);

    //user_info 패스워드 체크 - 문태일 수정
    @Select("select user_id from user_table where user_id = #{user_id} and user_pw = #{user_pw}")
    String checkPassword(@Param("user_id") String user_id,@Param("user_pw") String user_pw);

    //이 아래는 UserService(user_modify 뷰 출력정보들) - 문태일 수정
    @Select("select user_id as user_id, user_name as user_name, user_pw as user_pw, user_phone as user_phone, user_birth as user_birth  , user_email as user_email " +
            "from user_table where user_idx = #{user_idx}")
    UserBean getModifyUserBean(@Param("user_idx") int user_idx);

    @Update("update user_table set user_pw = #{user_pw} where user_idx = #{user_idx}")
    void modifyPassword(@Param("user_idx") int userIdx, @Param("user_pw") String newPassword);

    @Update("update user_table set user_email = #{user_email} where user_idx = #{user_idx}")
    void modifyEmail(@Param("user_idx") int userIdx, @Param("user_email") String newEmail);

    @Update("update user_table set user_phone = #{user_phone} where user_idx = #{user_idx}")
    void modifyPhone(@Param("user_idx") int userIdx, @Param("user_phone") String newPhone);

    //회원탈퇴
    @Update("update user_table set user_available = 'F' where user_idx = #{user_idx}")
    void deSignUp(int user_idx);
}
