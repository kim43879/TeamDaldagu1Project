package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.*;
import com.Daldagu1.TeamDaldagu1Project.mapper.GoodsMapper;
import com.Daldagu1.TeamDaldagu1Project.mapper.MainPageMapper;
import com.Daldagu1.TeamDaldagu1Project.mapper.OptionMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@PropertySource("classpath:imgPath.properties")
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private OptionMapper optionMapper;

    @Autowired
    private MainPageMapper mainPageMapper;

    @Value("${imgPath}")
    private String goodsApplyPath;

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

    public String[] getMainTags(Integer user_idx){
        if(user_idx == 0)
            return mainPageMapper.getMainTagsByNormal();
        else {
            String[] tags = mainPageMapper.getMainTagsByUser(user_idx);
            System.out.println("태그 개수 : " + tags.length);

            if(tags.length == 0)
                return mainPageMapper.getMainTagsByNormal();

            Set<String> tagSet = new HashSet<String>();

            for(String tag : tags){
                tagSet.add(tag);
                if(tagSet.size() == 3)
                    return tagSet.toArray(new String[tagSet.size()]);
            }

            tags = mainPageMapper.getMainTagsByNormal();

            for(String tag : tags){
                tagSet.add(tag);
                if(tagSet.size() == 3)
                    return tagSet.toArray(new String[tagSet.size()]);
            }
            return tags;
        }
    }

    public List<GoodsBean> getGoodsListByTag(String goods_tag){
        return goodsMapper.getGoodsListByTag(goods_tag);
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

    public void deleteGoods(int goods_idx){
        goodsMapper.deleteGoods(goods_idx);
    }

}
