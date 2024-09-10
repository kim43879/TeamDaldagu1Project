package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.NoticeBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper {
    
    //공지등록
    @Insert("insert into notice_table(notice_idx, notice_title, notice_text, notice_writer_idx, notice_date) " +
            "values(notice_seq.nextval, #{notice_title}, #{notice_text}, #{notice_writer_idx}, sysdate)")
    void addNotice(NoticeBean addNoticeInfoBean);

    //공지목록
    @Select("select * from notice_table order by notice_idx desc")
    List<NoticeBean> getNoticeList();

    //1개
    @Select("select * from notice_table where notice_idx= #{notice_idx}")
    NoticeBean getNotice(int notice_idx);

    //공지수정
    @Update("update notice_table set notice_title= #{notice_title}, notice_text= #{notice_text} " +
            "where notice_idx= #{notice_idx}")
    void modifyNotice(NoticeBean modifyNoticeBean);

    //공지삭제
    @Delete("delete from notice_table where notice_idx= #{notice_idx}")
    void deleteNotice(int notice_idx);

}
