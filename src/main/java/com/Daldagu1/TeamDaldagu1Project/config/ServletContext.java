package com.Daldagu1.TeamDaldagu1Project.config;

import jakarta.servlet.SessionCookieConfig;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;

public class ServletContext {
    @Bean
    public ServletContextInitializer initializer() {
        return servletContext -> {
            SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
            sessionCookieConfig.setSecure(true);
            sessionCookieConfig.setHttpOnly(true);
            //sessionCookieConfig.setSameSite("None"); // SameSite=None 설정

        };
    }
}
