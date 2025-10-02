package com.kandan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecipientRepository {
    public List<String> getAllEmails() {
    	List<String> emails = new ArrayList<>();
    	String sql = "SELECT email FROM subscribers";
    	try(Connection connectionObject = DBConnection.getConnection();
    		PreparedStatement pStatement = connectionObject.prepareStatement(sql);
    		ResultSet resultSet = pStatement.executeQuery()) {
    		  while (resultSet.next()) {
    			  emails.add(resultSet.getString("email"));
    		  }
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return emails;
    }
}
