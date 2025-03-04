package com.Daldagu1.TeamDaldagu1Project.config;

import com.Daldagu1.TeamDaldagu1Project.beans.AdminBean;
import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import com.Daldagu1.TeamDaldagu1Project.interceptor.AdminInterceptor;
import com.Daldagu1.TeamDaldagu1Project.interceptor.HeaderInterceptor;
import com.Daldagu1.TeamDaldagu1Project.interceptor.SellerPageAuthInterceptor;
import com.Daldagu1.TeamDaldagu1Project.interceptor.UserPageAuthInterceptor;
import jakarta.annotation.Resource;
import jakarta.servlet.SessionCookieConfig;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@ComponentScan("com.Daldagu1.TeamDaldagu1Project.controller")
public class ServletContext implements WebMvcConfigurer {

    @Resource(name = "loginUserBean")
    private UserBean loginUserBean;

    @Resource(name = "loginAdminBean")
    private AdminBean adminBean;

    @Bean
    public ServletContextInitializer initializer() {
        return servletContext -> {
            SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
            sessionCookieConfig.setSecure(true);
            sessionCookieConfig.setHttpOnly(true);
            //sessionCookieConfig.setSameSite("None"); // SameSite=None 설정
        };
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("file:src/main/resources/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);

        HeaderInterceptor headerInterceptor = new HeaderInterceptor(loginUserBean);
        InterceptorRegistration reg1 = registry.addInterceptor(headerInterceptor);
        reg1.addPathPatterns("/**");

        UserPageAuthInterceptor userPageAuthInterceptor = new UserPageAuthInterceptor(loginUserBean);
        InterceptorRegistration reg2 = registry.addInterceptor(userPageAuthInterceptor);
        reg2.addPathPatterns("/user/*");
        reg2.excludePathPatterns("/user/login","/user/join","user/status/*","/user/logout","/user/login_pro","/user/join_pro");

        SellerPageAuthInterceptor sellerPageAuthInterceptor = new SellerPageAuthInterceptor(loginUserBean);
        InterceptorRegistration reg3 = registry.addInterceptor(sellerPageAuthInterceptor);
        reg3.addPathPatterns("/seller/*");
        reg3.excludePathPatterns("/seller/seller_join","/seller/seller_join_pro");

        AdminInterceptor adminInterceptor = new AdminInterceptor(adminBean);
        InterceptorRegistration reg4 = registry.addInterceptor(adminInterceptor);
        reg4.addPathPatterns("/admin/*");
        reg4.excludePathPatterns("/admin/login");
    }
}
