package com.kandan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EmailServlet")
public class EmailServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   Connection connectionObject = null;
   ResultSet result = null;
   PreparedStatement pStatement = null;
   PreparedStatement ps = null;

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    String email = request.getParameter("email");
	    try {
	        connectionObject = DBConnection.getConnection();
	        
    		pStatement = connectionObject.prepareStatement("SELECT COUNT(*) FROM subscribers WHERE email = ?");
	        pStatement.setString(1, email);
    		result = pStatement.executeQuery();
	        result.next();
    		
	        if(result.getInt(1) > 0) {
	        	
	        	 response.sendRedirect("index.html?status=warning");
	        	 
	        } else {
	        	
    		     ps = connectionObject.prepareStatement("INSERT INTO subscribers(email) VALUES(?)");
	             ps.setString(1, email);
	             ps.executeUpdate();
	             
    		     response.sendRedirect("index.html?status=success");
    		     
	        }
	    } catch(Exception e) {
	    	e.printStackTrace();
	    	response.sendRedirect("index.html?status=warning");
	    }
	    finally {
	    	
	    	try {
	    	  if(result != null)
	    		  result.close();
	    	} catch(Exception e) {}
	    	
	    	try {
	    		if(pStatement != null)
	    			pStatement.close();	    		
	    	} catch(Exception e) {}
	    	
	    	try {
	    		if(ps != null) {
	    			ps.close();
	    		}
	    	} catch(Exception e) {}
	    	
	    	try {
	    		if(connectionObject != null)
	    			connectionObject.close();
	    	} catch(Exception e) {}
	    	
	    }
   }
}

















