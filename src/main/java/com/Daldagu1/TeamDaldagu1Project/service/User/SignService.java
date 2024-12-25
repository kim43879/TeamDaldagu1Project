package com.Daldagu1.TeamDaldagu1Project.service.User;

import com.Daldagu1.TeamDaldagu1Project.beans.AddrBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddrMapper addrMapper;

    @Autowired
    private MembershipMapper membershipMapper;

    @Autowired
    private SellerMapper sellerMapper;

    @Autowired
    private GoodsMapper goodsMapper;


    //회원가입 로직
    public void addUser(UserBean userBean){

        String phone1 = userBean.getUser_phone().substring(0,3);
        String phone2 = userBean.getUser_phone().substring(3,7);
        String phone3 = userBean.getUser_phone().substring(7,11);
        String phone = phone1 + "-" + phone2 + "-" + phone3;

        userBean.setUser_phone(phone);
        userMapper.addUser(userBean);

        AddrBean addrBean = new AddrBean();

        addrBean.setUser_idx(userMapper.getUserIdx(userBean.getUser_id()));
        String userAddr = userBean.getUser_addr1() + ", " + userBean.getUser_addr2();
        addrBean.setUser_addr(userAddr);
        addrBean.setUser_post(userBean.getUser_post());

        addrBean.setAddr_name("기본");
        addrBean.setAddr_main("T");
        addrBean.setAddr_phone(userBean.getUser_phone());

        addrMapper.addAddr(addrBean);
    }

    // 로그인 로직
    public UserBean getUserbyId(String user_id){
        UserBean tempLoginUserBean = userMapper.getUserbyId(user_id);
        if(tempLoginUserBean != null) {
            if (tempLoginUserBean.getUser_role().equals("S")) {
                tempLoginUserBean.setSeller_idx(userMapper.getSellerIdx(tempLoginUserBean.getUser_idx()));

                int membership_idx = tempLoginUserBean.getMembership_idx();
                String userMembership = membershipMapper.getMembership(membership_idx);
                tempLoginUserBean.setMembership(userMembership);
                int usedMoneyByMonth = membershipMapper.getUsedMoneyByMonth(tempLoginUserBean.getUser_idx());
                tempLoginUserBean.setUsedMoneyByMonth(String.format("%,d", usedMoneyByMonth));

                System.out.println(tempLoginUserBean.getSeller_idx());
            }
        }
        return tempLoginUserBean;
    }
    //회원가입 아이디 중복확인
    public String getUserId(String user_id){
        return userMapper.getUserId(user_id);
    }

    //회원탈퇴 로직(유저)
    public void deSignUp(int user_idx){
        int seller_idx = userMapper.getSellerIdx(user_idx);

        userMapper.deSignUp(user_idx);
        sellerMapper.deSignUp(seller_idx);
        goodsMapper.deSignUp(seller_idx);
    }
}
