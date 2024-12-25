package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.AddrBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddrMapper {
    //배송지 추가
    @Insert("insert into addr_table (addr_idx, user_idx, user_addr, user_post, addr_name, addr_main, addr_phone)" +
            "values (addr_seq.nextval, #{user_idx}, #{user_addr}, #{user_post}, #{addr_name}, #{addr_main}, #{addr_phone} )")
    void addAddr(AddrBean addrBean);

    //배송지 호출
    @Select("select * from addr_table where user_idx=#{user_idx} order by addr_main desc")
    List<AddrBean> getExtraUserAddr(int user_idx);

    @Select("select * from addr_table where addr_idx=#{addr_idx}")
    AddrBean getAddrByAddrIdx(int addr_idx);

    //기본 배송지 해제(배송지 수정으로 기본배송지 바꿀 때 실행)------------------------------------------------------
    @Select("select addr_idx from addr_table where user_idx=#{user_idx} and addr_main = 'T'")
    int getMainAddrIdx(int user_idx);

    @Update("update addr_table set addr_main='F' where addr_idx=#{addr_idx}")
    void addrMainFalse(int addr_idx);
    //------------------------------------------------------------------------------------------------------

    //배송지 수정
    @Update("update addr_table set user_addr=#{user_addr}, user_post=#{user_post}, addr_phone=#{addr_phone}, " +
            "addr_name=#{addr_name}, addr_main=#{addr_main} where addr_idx=#{addr_idx}")
    void updateAddr(AddrBean addrBean);

    @Delete("delete from addr_table where addr_idx = #{addr_idx}")
    void deleteAddr(int addr_idx);

    //유저가 가진 배송지 갯수 가져오기(배송지정보는 유저당 3개까지만 등록가능)
    @Select("select count(*) from addr_table where user_idx = #{user_idx}")
    int getAddrCount(int uesr_idx);

}
