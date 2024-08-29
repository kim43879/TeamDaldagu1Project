package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.AddGoodsInfo;
import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import com.Daldagu1.TeamDaldagu1Project.beans.PageBean;
import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.GoodsMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@PropertySource("classpath:imgPath.properties")
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Value("${imgPath}")
    private String goodsApplyPath;

    @Value("${page.paginationCnt}")
    private int paginationCnt;

    //상품 추가
    public void addGoodsInfo(GoodsBean addGoodsBean) {
        goodsMapper.addGoodsInfo(addGoodsBean);
    }

    private String saveApplyGoodsInfo(MultipartFile applyGoodsImg){
        String file_name = System.currentTimeMillis() + "_" + applyGoodsImg.getOriginalFilename();

        try{
            applyGoodsImg.transferTo(new File(goodsApplyPath + "/goods/" + file_name));
        } catch (Exception e){
            e.printStackTrace();
        }
        return file_name;
    }

    //상품 등록 정보 추가
    public void addGoodsInfoApply(AddGoodsInfo addGoodsInfoBean) {
        MultipartFile uploadFile = addGoodsInfoBean.getGoods_img_file();
        if(uploadFile.getSize() > 0){
            String file_name = saveApplyGoodsInfo(uploadFile);
            addGoodsInfoBean.setGoods_img(file_name);
        }else{
            addGoodsInfoBean.setGoods_img("default_goods_img.jpg");
        }
        goodsMapper.addAddGoodsInfo(addGoodsInfoBean);
    }

    //구매상품 호출
    public GoodsBean getPurchaseGoods(int goods_idx) {
        return goodsMapper.getPurchaseGoods(goods_idx);
    }

    public List<AddGoodsInfo> getAddGoodsInfoList(){
        return goodsMapper.getAddGoodsList();
    }

    public List<GoodsBean> getMyGoodsList(int seller_idx){
        return goodsMapper.getMyGoodsList(seller_idx);
    }

    public List<GoodsBean> getGoodsListByTag(String goods_tag){
        return goodsMapper.getGoodsListByTag(goods_tag);
    }

    public List<GoodsBean> searchGoodsList(SearchBean searchBean,int page){

        int start = (page - 1) * searchBean.getShowCount();

        RowBounds rowBounds = new RowBounds(start,(start + searchBean.getShowCount()));

        if(searchBean.getSearchCategory().equals("전체")){
            searchBean.setSearchCategory("%");
        }
        if(searchBean.getSearchMaxPrice() == 0){
            searchBean.setSearchMaxPrice(Integer.MAX_VALUE);
        }

        searchBean.showField();

        if(searchBean.getSortType().equals("goods_price1")){
            return goodsMapper.searchGoodsListOrder_price1(searchBean, rowBounds);
        }else if(searchBean.getSortType().equals("goods_price2")){
            return goodsMapper.searchGoodsListOrder_price2(searchBean, rowBounds);
        }

        return goodsMapper.searchGoodsList(searchBean,rowBounds);
    }

    public AddGoodsInfo getAddGoodsInfo(int info_idx){
        return goodsMapper.getAddGoodsByIdx(info_idx);
    }
    public void deleteAddGoodsInfo(int info_idx){
        goodsMapper.deleteAddGoods(info_idx);
    }

    public PageBean getSearchPageCount(int currentPage,SearchBean searchBean){
        if(searchBean.getSearchCategory().equals("goods_price1")){
            return new PageBean(goodsMapper.searchGoodsListOrder_priceCnt(searchBean), currentPage, searchBean.getShowCount(), paginationCnt);
        }
        else if(searchBean.getSearchCategory().equals("goods_price2")){
            return new PageBean(goodsMapper.searchGoodsListOrder_price2Cnt(searchBean), currentPage, searchBean.getShowCount(), paginationCnt);
        }
        return new PageBean(goodsMapper.searchGoodsListCnt(searchBean),currentPage,searchBean.getShowCount(),paginationCnt);
    }

    public int getTotalGoodsCnt(SearchBean searchBean){
        if(searchBean.getSearchCategory().equals("goods_price1")){
            return goodsMapper.searchGoodsListOrder_priceCnt(searchBean);
        }
        else if(searchBean.getSearchCategory().equals("goods_price2")){
            return goodsMapper.searchGoodsListOrder_price2Cnt(searchBean);
        }
        return goodsMapper.searchGoodsListCnt(searchBean);
    }
}
