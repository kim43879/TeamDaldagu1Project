package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.BannerBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BannerMapper {

    //등록
    @Insert("insert into banner_table(banner_idx, banner_name, banner_img, goods_idx, goods_name, banner_status) " +
            "values(banner_seq.nextval, #{banner_name}, #{banner_img}, #{goods_idx}, #{goods_name}, 0)")
    void addBannerInfo(BannerBean addBannerInfoBean);
    
    //배너등록 승인
    @Update("update banner_table set banner_status= 1 where banner_idx= #{banner_idx}")
    void getBannerInfo(int banner_idx);

    //배너등록 반려
    @Delete("delete from banner_table where banner_idx= #{banner_idx}")
    void refusedBanner(int banner_idx);

    //반환
    @Select("select * from banner_table where banner_status = 1")
    List<BannerBean> getBannerList();

    @Select("select * from banner_table")
    List<BannerBean> adminGetBannerList();

    //삭제
    @Delete("delete from banner_table where banner_idx = #{banner_idx}")
    void deleteBanner(int banner_idx);


}
