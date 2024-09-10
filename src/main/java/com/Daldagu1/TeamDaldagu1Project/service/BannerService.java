package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.BannerBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.BannerMapper;
import com.Daldagu1.TeamDaldagu1Project.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@PropertySource("classpath:imgPath.properties")
public class BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Value("${imgPath}")
    private String bannerImgPath;

    //배너추가
    public void addBannerInfo(BannerBean addBannerBean) {
        String img_name = saveFileBanner(addBannerBean.getBanner_file());
        String goods_name = goodsMapper.getPurchaseGoods(addBannerBean.getGoods_idx()).getGoods_name();

        addBannerBean.setBanner_img(img_name);
        addBannerBean.setGoods_name(goods_name);

        bannerMapper.addBannerInfo(addBannerBean);
    }

    //배너 업로드
    private String saveFileBanner(MultipartFile bannerImg) {
        String file_name = System.currentTimeMillis() + "_" + bannerImg.getOriginalFilename();

        try {
            bannerImg.transferTo(new File(bannerImgPath + "/banner/" + file_name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file_name;
    }

    //배너 등록
    public void getBannerInfo(int banner_idx) {
        bannerMapper.getBannerInfo(banner_idx);
    }

    //배너 등록 반려
    public void refusedBanner(int banner_idx) {
        bannerMapper.refusedBanner(banner_idx);
    }

    //승인된 배너 등록 정보
    public void bannerInfo(BannerBean addBannerInfo) {
        MultipartFile uploadFile = addBannerInfo.getBanner_file();

        if(uploadFile.getSize() > 0) {
            String file_name = saveFileBanner(uploadFile);
            addBannerInfo.setBanner_img(file_name);
        }
        bannerMapper.addBannerInfo(addBannerInfo);
    }

    //메인페이지 배너 호출
    public List<BannerBean> getBannerList() {
        return bannerMapper.getBannerList();
    }

    //승인 정보 호출
    public List<BannerBean> adminGetBannerList() {
        return bannerMapper.adminGetBannerList();
    }

    public void deleteBanner(int banner_idx){
        bannerMapper.deleteBanner(banner_idx);
    }

}//class
