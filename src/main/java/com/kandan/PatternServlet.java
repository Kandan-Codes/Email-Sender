package com.kandan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PatternServlet")
public class PatternServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ResultSet result = null;
		Connection connectionObject = null;
		PreparedStatement pStatement = null;
		PreparedStatement ps = null;
		
    	String title = request.getParameter("title");
    	String description = request.getParameter("description");
    	String code = request.getParameter("code");
    	String explanation = request.getParameter("explanation");
    	
    	try {
    	    connectionObject = DBConnection.getConnection();
    	    pStatement = connectionObject.prepareStatement("SELECT COUNT(*) FROM patterns WHERE pattern_title = ?");
    	    pStatement.setString(1, title);
    	    result = pStatement.executeQuery();
    	    result.next();
    	    
    	    if(result.getInt(1) > 0) {
    	    	
    	    	response.sendRedirect("index.html?status=warning");
    	    	
    	    
    	    } else {
    	    	
    	        ps = connectionObject.prepareStatement("INSERT INTO patterns(pattern_title, pattern_description, pattern_code, pattern_code_explanation) VALUES (?, ?, ?, ?)");
    		
    	        ps.setString(1, title);
    		    ps.setString(2, description);
    		    ps.setString(3, code);
    		    ps.setString(4, explanation);
    		    ps.executeUpdate();
    		
    		    response.sendRedirect("index.html?status=success");
    		    
    	    }
    	} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("index.html?status=warning");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
