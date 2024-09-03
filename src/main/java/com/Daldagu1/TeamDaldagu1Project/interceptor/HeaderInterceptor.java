package com.Daldagu1.TeamDaldagu1Project.interceptor;

import com.Daldagu1.TeamDaldagu1Project.beans.SearchBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class HeaderInterceptor implements HandlerInterceptor {
    private UserBean loginUserBean;

    public HeaderInterceptor(UserBean loginUserBean) {
        this.loginUserBean = loginUserBean;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        request.setAttribute("loginUserBean", loginUserBean);
        return true;
    }
}
