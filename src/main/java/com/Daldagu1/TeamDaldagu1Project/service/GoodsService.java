package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.mapper.GoodsMapper;
import com.Daldagu1.TeamDaldagu1Project.mapper.OptionMapper;
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

    @Autowired
    private OptionMapper optionMapper;

    @Value("${imgPath}")
    private String goodsApplyPath;

    @Value("${page.paginationCnt}")
    private int paginationCnt;

    //상품 추가
    public void addGoodsInfo(GoodsBean addGoodsBean) {
        goodsMapper.addGoodsInfo(addGoodsBean);
        OptionBean tempOptionBean = new OptionBean();
        tempOptionBean.setGoods_idx(goodsMapper.getGoodsIdx(addGoodsBean.getGoods_img()));
        tempOptionBean.setOption_name("기본");
        tempOptionBean.setOption_price(0);
        optionMapper.addOption(tempOptionBean);
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
        MultipartFile uploadFile1 = addGoodsInfoBean.getGoods_img_file();
        MultipartFile uploadFile2 = addGoodsInfoBean.getGoods_img_file2();
        if(uploadFile1.getSize() > 0 && uploadFile2.getSize() > 0){
            String file_name1 = saveApplyGoodsInfo(uploadFile1);
            String file_name2 = saveApplyGoodsInfo(uploadFile2);
            addGoodsInfoBean.setGoods_img(file_name1);
            addGoodsInfoBean.setGoods_img2(file_name2);
        }else{
            addGoodsInfoBean.setGoods_img("default_goods_img.jpg");
        }
        goodsMapper.addAddGoodsInfo(addGoodsInfoBean);
    }

    //구매상품 호출
    public GoodsBean getPurchaseGoods(int goods_idx) {
        GoodsBean tempGoodsBean = goodsMapper.getPurchaseGoods(goods_idx);
        if(tempGoodsBean == null){
            return null;
        }
        tempGoodsBean.setGoods_option(optionMapper.getOptionList(goods_idx));
        return tempGoodsBean;
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

        RowBounds rowBounds = new RowBounds(start,(searchBean.getShowCount()));

        if(searchBean.getSearchCategory().equals("전체")){
            searchBean.setSearchCategory("%");
        }
        if(searchBean.getSearchMaxPrice() == 0){
            searchBean.setSearchMaxPrice(300000);
        }

        if(searchBean.getSortType().equals("goods_price1")){
            return goodsMapper.searchGoodsListOrder_price1(searchBean, rowBounds);
        }else if(searchBean.getSortType().equals("goods_price2")){
            return goodsMapper.searchGoodsListOrder_price2(searchBean, rowBounds);
        }

        return goodsMapper.searchGoodsList(searchBean,rowBounds);
    }

    public void updateGoodsInfo(GoodsBean updateGoodsBean){
        if(updateGoodsBean.getGoods_img2() == null){
            updateGoodsBean.setGoods_img2(goodsMapper.getPurchaseGoods(updateGoodsBean.getGoods_idx()).getGoods_img2());
        }else{
            updateGoodsBean.setGoods_img2(saveApplyGoodsInfo(updateGoodsBean.getPage_img()));
        }
        goodsMapper.updateGoodsInfo(updateGoodsBean);
    }

    public AddGoodsInfo getAddGoodsInfo(int info_idx){
        return goodsMapper.getAddGoodsByIdx(info_idx);
    }
    public void deleteAddGoodsInfo(int info_idx){
        goodsMapper.deleteAddGoods(info_idx);
    }

    public int goodsCountBySellerIdx(int seller_idx){
        return goodsMapper.goodsCountBySellerIdx(seller_idx);
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

    public void deleteGoods(int goods_idx){
        goodsMapper.deleteGoods(goods_idx);
    }
}
