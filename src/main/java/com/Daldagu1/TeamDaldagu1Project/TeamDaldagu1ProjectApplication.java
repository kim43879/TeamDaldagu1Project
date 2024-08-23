package com.Daldagu1.TeamDaldagu1Project;

import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.SessionScope;

@SpringBootApplication
public class TeamDaldagu1ProjectApplication {

	@Bean("loginUserBean")
	@SessionScope
	public UserBean loginUserBean(){
		return new UserBean(false);
	}

	public static void main(String[] args) {
		SpringApplication.run(TeamDaldagu1ProjectApplication.class, args);
	}
}
