package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import utils.Utils;


public class SQL_connection {
	Connection connection;
	Statement statement;
	//MOVED HERE FROM SQL_connection
	String URI = "jdbc:mysql://localhost:3306/turbo_bathroom_db?autoReconnect=true&user=fredagsmys&password=123&serverTimezone=GMT%2b2";

	public SQL_connection() throws SQLException {
		
		connection = DriverManager.getConnection(URI);
		statement = connection.createStatement();
	}
	
	public int insert_stamp(String wc) throws SQLException{
		//NEW: OPEN AND CLOSE CONN EVERYTIME 
		connection = DriverManager.getConnection(URI);
		statement = connection.createStatement();
		//END NEW
		String insertString = String.format(Locale.US,
				"INSERT INTO stamps (BATHROOM_NAME) VALUES (%s);", '"' + wc + '"');
		int status = statement.executeUpdate(insertString);
		//CLOSE EVERYTIME
		connection.close();
		return status;
	}
	
	public String get_users() throws SQLException {
		String getString = "SELECT NAME FROM users";
		ResultSet resultSet = statement.executeQuery(getString);
		String userList = "";
		while (resultSet.next()) {
			userList += resultSet.getString(1) + ',';
		}
		return userList;
	}
	
	public String get_bathrooms() throws SQLException {
		//NEW: OPEN AND CLOSE CONN EVERYTIME 
		connection = DriverManager.getConnection(URI);
		statement = connection.createStatement();
		//END NEW
		String getString = "SELECT NAME FROM bathrooms";
		ResultSet resultSet = statement.executeQuery(getString);
		String userList = "";
		while (resultSet.next()) {
			userList += resultSet.getString(1) + ',';
		}
		//CLOSE EVERYTIME
		connection.close();
		return userList;
	}
	
	public List<Stamp> get_stamps() throws SQLException {

		//NEW: OPEN AND CLOSE CONN EVERYTIME 
		connection = DriverManager.getConnection(URI);
		statement = connection.createStatement();
		//END NEW

		String getString = "SELECT  BATHROOM_NAME, DATE FROM stamps";
		ResultSet resultSet = statement.executeQuery(getString);
		List<Stamp> stamps = new LinkedList<>();
		String bathroom, date;
		while (resultSet.next()) {
			bathroom = resultSet.getString(1);
			date = resultSet.getString(2);
			Stamp stamp = new Stamp(bathroom, date);
			stamps.add(stamp);
		}
		//CLOSE EVERYTIME
		connection.close();
		return stamps;
	}

	public List<Stamp> get_latest_stamps()throws SQLException{
		//NEW: OPEN AND CLOSE CONN EVERYTIME 
		connection = DriverManager.getConnection(URI);
		statement = connection.createStatement();
		//END NEW
		String currDate = Utils.getCurrTime();
		String getString = String.format(
				"SELECT BATHROOM_NAME, DATE, TIMEDIFF(%s,DATE)"
				+ " FROM stamps WHERE ID IN (SELECT MAX(ID) FROM stamps GROUP BY BATHROOM_NAME);",'"' + currDate + '"');
		ResultSet resultSet = statement.executeQuery(getString);
		List<Stamp> stamps = new LinkedList<>();
		String bathroom, date, timeDiff;
		while (resultSet.next()) {
			bathroom = resultSet.getString(1);
			date = resultSet.getString(2);
			timeDiff = resultSet.getString(3);
			
			Stamp stamp = new Stamp(bathroom, date, timeDiff);
			stamps.add(stamp);
		}
		//CLOSE EVERYTIME
		connection.close();
		return stamps;
		
	}
	public Stamp get_latest_stamp(String bathroom) throws SQLException{
		//NEW: OPEN AND CLOSE CONN EVERYTIME 
		connection = DriverManager.getConnection(URI);
		statement = connection.createStatement();
		//END NEW
		String currDate = Utils.getCurrTime();
		String getString = String.format(
				"SELECT DATE, TIMEDIFF(%s,DATE) "
				+ "FROM stamps WHERE BATHROOM_NAME = %s ORDER BY ID DESC LIMIT 1;",'"' + currDate + '"', '"' + bathroom + '"');
	
		ResultSet resultSet = statement.executeQuery(getString);
		String date = "", timeDiff = "";
		if (resultSet.next()){
			date = resultSet.getString(1);
			timeDiff = resultSet.getString(2);
		}
		Stamp stamp = new Stamp(bathroom, date, timeDiff);
		//CLOSE EVERYTIME
		connection.close();
		return stamp;
		
	}
	
	public int insert_user(String name) throws SQLException {
		String insertString = String.format(Locale.US,"INSERT INTO users (NAME) VALUES (%s)", name);
		return statement.executeUpdate(insertString);
	}
	
	public int insert_bathroom(String name) throws SQLException {
		//NEW: OPEN AND CLOSE CONN EVERYTIME 
		connection = DriverManager.getConnection(URI);
		statement = connection.createStatement();
		//END NEW
		String insertString = String.format(Locale.US,"INSERT INTO bathrooms (NAME) VALUES (%s)", name);
		
		int status = statement.executeUpdate(insertString);
		//CLOSE EVERYTIME
		connection.close();
		return status;
	}

}
