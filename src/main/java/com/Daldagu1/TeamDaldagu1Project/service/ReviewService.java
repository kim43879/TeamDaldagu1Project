package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.PageBean;
import com.Daldagu1.TeamDaldagu1Project.beans.ReviewBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.ReviewMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@PropertySource("classpath:imgPath.properties")
@Service
public class ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    //@Value("${page.listcnt}")
    private int page_list =  5;

    //@Value("#{page.paginationCnt}")
    private int paginationcnt = 10;

    @Value("${imgPath}")
    private String path_upload;

    private String saveUploadFile(MultipartFile upload_file) {

        String file_name = System.currentTimeMillis() + "_" + upload_file.getOriginalFilename();

        try {
            upload_file.transferTo(new File(path_upload + "/upload/" + file_name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file_name;
    }

    public void addReview(ReviewBean reviewBean){

        MultipartFile upload_file = reviewBean.getReview_file();

        if(upload_file.getSize()>0){

            String file_name = saveUploadFile(upload_file);
            reviewBean.setReview_img(file_name);
        }

        reviewMapper.addReview(reviewBean);
    }
    public List<ReviewBean> getReviewList(int goods_idx, int page){

        int start = (page - 1) * page_list;
        RowBounds rowBounds = new RowBounds(start, page_list);

        return reviewMapper.getReviewList(goods_idx, rowBounds);
    }

    public List<ReviewBean> getReviewListForSeller(int seller_idx){

        return reviewMapper.getReviewListForSeller(seller_idx);
    }

    public List<ReviewBean> getReviewListForUser(int user_idx){
        return reviewMapper.getReviewListForUser(user_idx);
    }
    public PageBean getReviewCount(int goods_idx, int current_page){

        int reviewCount = reviewMapper.getReviewCount(goods_idx);
        System.out.println("페이지 수 : " + reviewCount);
        PageBean pageBean = new PageBean(reviewCount, current_page, page_list, paginationcnt);

        return pageBean;
    }

    public PageBean getReviewCountForUser(int user_idx, int current_page){
        int reviewCount = reviewMapper.getReviewCountByUser(user_idx);
        PageBean pageBean = new PageBean(reviewCount, current_page, page_list, paginationcnt);
        return pageBean;
    }

    public int getReviewCountForUser(int user_idx){
        return reviewMapper.getReviewCountByUser(user_idx);
    }
}