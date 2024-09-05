package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.OptionBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.OptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {
    @Autowired
    OptionMapper optionMapper;

    public void addOption(OptionBean optionBean){
        optionMapper.addOption(optionBean);
    }

    public List<OptionBean> getOptionList(int goods_idx){
        return optionMapper.getOptionList(goods_idx);
    }

    public int getOptionCount(int goods_idx){
        return optionMapper.getOptionCount(goods_idx);
    }

    public void deleteOption(int option_idx){
        optionMapper.deleteOption(option_idx);
    }

}
