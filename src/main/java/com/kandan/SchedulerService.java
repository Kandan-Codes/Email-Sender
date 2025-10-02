package com.kandan;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerService {
    private final PatternService patternService;
    private final EmailService emailService;
    private final RecipientRepository recipientRepo;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    
    public SchedulerService(PatternService patternService, EmailService emailService, RecipientRepository recipientRepo) {
    	this.patternService = patternService;
    	this.emailService = emailService;
    	this.recipientRepo = recipientRepo;
    }

	public void startDailyJobAt(int scheduleHour24, int minute, boolean testMode) {
    	Runnable task = () -> {
    		try {
    			Pattern pattern = patternService.getAndMarkNextPattern();
    			if(pattern != null) {
    			List<String> allEmails = recipientRepo.getAllEmails();
    			emailService.sendPatternToRecipients(allEmails, pattern);
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	};
    	
    	if(testMode) {
    		scheduler.schedule(task, 20, TimeUnit.SECONDS);
    		System.out.println("Test Mode: Job will run after 5 seconds....");
    	} else {
    		LocalDateTime now = LocalDateTime.now();
    		LocalDateTime firstRun = now.withHour(scheduleHour24).withMinute(minute).withSecond(0);
    		//if the time is already passed today, schedule for tomorrow
    		if(now.compareTo(firstRun) >= 0) {
    			firstRun = firstRun.plusDays(1);
    		}
    		
    		long initialDelay = Duration.between(now, firstRun).getSeconds();
    		long period = TimeUnit.DAYS.toSeconds(1);
    		scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    	}
    }
    
    
    public void shutDown() {
    	scheduler.shutdown();
    }
}