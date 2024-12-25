package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.GoodsBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportMapper reportMapper;

    public List<GoodsBean> getReportedGoodsList(){
        return reportMapper.getReportedGoodsList();
    }

    public void addReportCount(int goods_idx){
        reportMapper.addReportCount(goods_idx);
    }

    public void removeReportCount(int goods_idx){
        reportMapper.removeReportCount(goods_idx);
    }
}
