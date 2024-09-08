package com.Daldagu1.TeamDaldagu1Project.service;

import com.Daldagu1.TeamDaldagu1Project.mapper.MembershipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {

    @Autowired
    MembershipMapper membershipMapper;

    public float getMembershipPoint(int membership_idx){
        return membershipMapper.getMembershipPoint(membership_idx);

    }

}
