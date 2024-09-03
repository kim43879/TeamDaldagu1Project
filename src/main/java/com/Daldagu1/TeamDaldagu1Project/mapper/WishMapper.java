package com.Daldagu1.TeamDaldagu1Project.mapper;

import com.Daldagu1.TeamDaldagu1Project.beans.WishBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WishMapper {

    @Insert("insert into wish_table(wish_idx, goods_idx, user_idx, wish_available) " +
            "values(wish_seq.nextval, #{goods_idx}, #{user_idx}, 'T')")
    void addUserWish(WishBean userWishBean);

    @Select("select * from wish_table w, goods_table g " +
            "where w.goods_idx = g.goods_idx " +
            "and w.user_idx= #{user_idx} " +
            "and g.goods_available = 'T' " +
            "and w.wish_available = 'T'")
    List<WishBean> getUserWishList(int user_idx);

    @Delete("delete from wish_table where wish_idx= #{wish_idx}")
    void deleteUserWish(int wish_idx);
}
