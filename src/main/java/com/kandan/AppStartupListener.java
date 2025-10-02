package com.kandan;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppStartupListener implements ServletContextListener {
     SchedulerService schedulerService;
     
     @Override
     public void contextInitialized(ServletContextEvent sce) {
    	 
    	 //initialized repositories
    	 PatternRepository patternRepo = new PatternRepository();
    	 SentLogRepository sentLogRepo = new SentLogRepository();
    	 RecipientRepository recipientRepo = new RecipientRepository();
    	 
    	 //services
    	 PatternService patternService = new PatternService(patternRepo, sentLogRepo);
    	 
    	 //configure your SMTP Settings here
    	 String smtpHost = "smtp.gmail.com";
    	 int smtpPort = 587;
    	 String smtpUser = "mani535.416u@gmail.com";
    	 String smtpPass = "ejwj bvts dsuu nvvw";
    	 String fromAddress = "mani535.416u@gmail.com";
    	 
    	 EmailService emailService = new EmailService(smtpHost, smtpPort, smtpUser, smtpPass, fromAddress);
    	 
    	 schedulerService = new SchedulerService(patternService, emailService, recipientRepo);
    	 schedulerService.startDailyJobAt(8, 0, true);
     }
     
     @Override
     public void contextDestroyed(ServletContextEvent sce) {
    	 if(schedulerService != null) {
    		  schedulerService.shutDown();
    	 }
     }
}
