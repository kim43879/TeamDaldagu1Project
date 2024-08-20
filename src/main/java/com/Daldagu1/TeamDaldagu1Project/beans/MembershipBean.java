package com.Daldagu1.TeamDaldagu1Project.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembershipBean {
    private int membership_idx;         //멤버쉽 기본키
    private String membership_grade;    //멤버쉽 등급
    private int membership_point;       //적립률
}
