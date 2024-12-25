package com.Daldagu1.TeamDaldagu1Project.config;

import com.Daldagu1.TeamDaldagu1Project.service.MembershipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingConfig {

    private static final Logger logger = LoggerFactory.getLogger(SchedulingConfig.class);

    @Autowired
    private MembershipService membershipService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void run() {
        logger.info("이번달의 등급을 정산하겠습니다.");
        try {
            membershipService.updateMembership();
        } catch (Exception e) {
            logger.error("작업 수행 중 문제가 발생했습니다.", e);
        }
    }
}