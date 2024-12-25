package com.Daldagu1.TeamDaldagu1Project.interceptor;

import com.Daldagu1.TeamDaldagu1Project.beans.AdminBean;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {
    private AdminBean adminBean;

    public AdminInterceptor(AdminBean adminBean){
        this.adminBean = adminBean;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(adminBean.isLoginSuccess());
        if(!adminBean.isLoginSuccess()){
            response.sendRedirect("/admin/login");
        }
        return true;
    }
}
