package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.ReviewBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface ReviewMapper {

    //리뷰등록
    @Insert("insert into review_table (review_idx, review_title, review_score, review_img, review_content, goods_idx, user_idx, review_date) " +
            "values(review_seq.nextval, #{review_title}, #{review_score}, #{review_img, jdbcType=VARCHAR}, #{review_content}, #{goods_idx}, #{user_idx}, sysdate)")
    void addReview(ReviewBean reviewBean);

    //전체리뷰
    @Select("select u.user_idx, u.user_id, r.review_idx, r.review_title, r.review_content, r.review_score, r.review_img, r.review_date " +
            "from user_table u, review_table r " +
            "where u.user_idx = r.user_idx " +
            "and r.goods_idx = #{goods_idx} " +
            "order by r.review_idx desc")
    List<ReviewBean> getReviewList(int goods_idx, RowBounds rowBounds);

    //리뷰개수
    @Select("select count(*) from review_table where goods_idx = #{goods_idx}")
    int getReviewCount(int goods_idx);
}
