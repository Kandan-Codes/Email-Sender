package com.kandan;

public class Pattern {
	
   private int id;
   private String title;
   private String description;
   private String code;
   private String explanation;
   
   public Pattern(int id, String title, String description, String code, String explanation) {
	   this.id= id;
	   this.title = title;
	   this.description = description;
	   this.code = code;
	   this.explanation = explanation;
   }
   
   public int getId() { return id; }
   public String getTitle() { return title; }
   public String getDescription() { return description; }
   public String getCode() { return code; }
   public String getExplanation() { return explanation; }
   
}
