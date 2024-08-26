package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.AddGoodsInfo;
import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
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
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Value("${img.goods.applypath}")
    private String goodsApplyPath;

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
    public List<GoodsBean> getPurchaseGoods(int goods_idx) {
        return goodsMapper.getPurchaseGoods(goods_idx);
    }

    public List<AddGoodsInfo> getAddGoodsInfoList(){
        return goodsMapper.getAddGoodsList();
    }

    public AddGoodsInfo getAddGoodsInfo(int info_idx){
        return goodsMapper.getAddGoodsByIdx(info_idx);
    }
    public void deleteAddGoodsInfo(int info_idx){
        goodsMapper.deleteAddGoods(info_idx);
    }
}
