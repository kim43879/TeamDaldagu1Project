package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.beans.NoticeBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    
    //공지등록
    public void addNotice(NoticeBean addNoticeInfoBean) {
        noticeMapper.addNotice(addNoticeInfoBean);
    }
    
    //공지출력
    public List<NoticeBean> getNoticeList() {
        return noticeMapper.getNoticeList();
    }
    
    //단일
    public NoticeBean getNotice(int notice_idx) {
        return noticeMapper.getNotice(notice_idx);
    }

    //공지수정
    public void modifyNotice(NoticeBean modifyNoticeBean) {
        noticeMapper.modifyNotice(modifyNoticeBean);
    }

    //공지삭제
    public void deleteNotice(int notice_idx) {
        noticeMapper.deleteNotice(notice_idx);
    }

}//class
