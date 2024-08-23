package com.Daldagu1.TeamDaldagu1Project.interceptor;

import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class SellerPageAuthInterceptor implements HandlerInterceptor {

    private UserBean loginUserBean;

    public SellerPageAuthInterceptor(UserBean loginUserBean){
        this.loginUserBean = loginUserBean;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(loginUserBean.getUser_role().equals("D")){
            response.sendRedirect("/not_seller");
        }

        return true;
    }
}
