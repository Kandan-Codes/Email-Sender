package com.kandan;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class SentLogRepository {
   public void saveSentPattern(int patternId) {
	   String sql = "INSERT INTO sent_log(pattern_id, sent_date) VALUES(?, ?)";
	   try(Connection connectionObject = DBConnection.getConnection();
		   PreparedStatement pStatement = connectionObject.prepareStatement(sql)) {
		   pStatement.setInt(1, patternId);
		   Date currentDate = Date.valueOf(LocalDate.now());
		   pStatement.setDate(2, currentDate);
		   pStatement.executeUpdate();
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
   }
   
   public void clearSentLog() {
	   String sql = "TRUNCATE TABLE sent_log";
	   try(Connection connectionObject = DBConnection.getConnection();
		   PreparedStatement pStatement = connectionObject.prepareStatement(sql)) {
		   pStatement.executeUpdate();
	   } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
}
