package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.OptionBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.OptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {

    @Autowired
    private OptionMapper optionMapper;
    
    //옵션 추가
    public void addOption(OptionBean optionBean) {
        optionMapper.addOption(optionBean);
    }

    //옵션 출력
    public List<OptionBean> getOptionList(int goods_idx) {
        return optionMapper.getOptionList(goods_idx);
    }
}
