package com.kandan;

public class PatternService {
	
	private final PatternRepository patternRepo;
	private final SentLogRepository sentLogRepo;
    public PatternService(PatternRepository patternRepo, SentLogRepository sentLogRepo) {
    	this.patternRepo = patternRepo;
    	this.sentLogRepo = sentLogRepo;
    }
    
    public Pattern getAndMarkNextPattern() {
    	Pattern pattern = patternRepo.getNextUnsentPattern();
    	if(pattern == null) {
    		sentLogRepo.clearSentLog();
    		pattern = patternRepo.getFirstPattern();
    		if(pattern != null) {
    			sentLogRepo.saveSentPattern(pattern.getId());
    		}
    		return pattern;
    	} else {
    		sentLogRepo.saveSentPattern(pattern.getId());
    		return pattern;
    	}
    }
}
