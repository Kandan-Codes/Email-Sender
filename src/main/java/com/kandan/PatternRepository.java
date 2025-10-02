package com.kandan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatternRepository {
    public Pattern getNextUnsentPattern() {
    	String sql = "SELECT p.id, p.pattern_title, p.pattern_description, p.pattern_code, p.pattern_code_explanation FROM patterns AS p WHERE p.id NOT IN (SELECT pattern_id FROM sent_log) ORDER BY p.id ASC LIMIT 1";
    	try (Connection connectionObject = DBConnection.getConnection(); 
    	     PreparedStatement pStatement = connectionObject.prepareStatement(sql);
    		 ResultSet resultSet = pStatement.executeQuery()) {
    		 
    		 if(resultSet.next()) {
    			 return new Pattern(resultSet.getInt("id"), resultSet.getString("pattern_title"), resultSet.getString("pattern_description"),
    					 resultSet.getString("pattern_code"), resultSet.getString("pattern_code_explanation"));
    		 }
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return null;
    }
    
    
    public Pattern getFirstPattern() {
    	String sql = "SELECT id, pattern_title, pattern_description, pattern_code, pattern_code_explanation FROM patterns ORDER BY id ASC LIMIT 1";
    	try(Connection connectionObject = DBConnection.getConnection();
    		PreparedStatement pStatement = connectionObject.prepareStatement(sql);
    		ResultSet resultSet = pStatement.executeQuery()
    	   ) {
    		
    		if(resultSet.next()) {
    			return new Pattern(resultSet.getInt("id"), resultSet.getString("pattern_title"),
    		            resultSet.getString("pattern_description"), resultSet.getString("pattern_code"),
    		            resultSet.getString("pattern_code_explanation"));
    		}
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	return null;
    }
    
    
    
    
    
}







