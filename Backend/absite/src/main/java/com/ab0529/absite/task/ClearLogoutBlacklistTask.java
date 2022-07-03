package com.ab0529.absite.task;

import com.ab0529.absite.service.TokenBlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClearLogoutBlacklistTask {
	@Autowired
	TokenBlacklistService tokenBlacklistService;

	@Scheduled(cron = "0 0 12 ** ?")
	// Execute at 12:00 every day
	public void clearBlacklistTable() {
		tokenBlacklistService.deleteAll();
		log.info("==> TASK - CLEAR BLACKLIST TABLE");
	}
}
