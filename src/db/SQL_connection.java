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

	public SQL_connection() throws SQLException {
		String URI = "jdbc:mysql://localhost:3306/turbo_bathroom_db?autoReconnect=true&user=fredagsmys&password=123&serverTimezone=GMT%2b2";
		connection = DriverManager.getConnection(URI);
		statement = connection.createStatement();
		
		
	}
	
	public int insert_stamp(String user, String wc, String date) throws SQLException{
		String insertString = String.format(Locale.US,
				"INSERT INTO stamps (USER_NAME, BATHROOM_NAME, DATE) VALUES (%s, %s, %s);", '"' + user + '"', '"' + wc + '"', date);
		return statement.executeUpdate(insertString);
		
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
		String getString = "SELECT NAME FROM bathrooms";
		ResultSet resultSet = statement.executeQuery(getString);
		String userList = "";
		while (resultSet.next()) {
			userList += resultSet.getString(1) + ',';
		}
		return userList;
	}
	
	public List<Stamp> get_stamps() throws SQLException {
		String getString = "SELECT USER_NAME, BATHROOM_NAME, DATE FROM stamps";
		ResultSet resultSet = statement.executeQuery(getString);
		List<Stamp> stamps = new LinkedList<>();
		String user, bathroom, date;
		while (resultSet.next()) {
			user = resultSet.getString(1);
			bathroom = resultSet.getString(2);
			date = resultSet.getString(3);
			Stamp stamp = new Stamp(user, bathroom, date);
			stamps.add(stamp);
		}
		return stamps;
	}
	public List<Stamp> get_latest_stamps()throws SQLException{
		String currDate = Utils.getCurrTime();
		String getString = String.format(
				"SELECT USER_NAME, BATHROOM_NAME, DATE, TIMEDIFF(%s,DATE)"
				+ " FROM stamps WHERE ID IN (SELECT MAX(ID) FROM stamps GROUP BY BATHROOM_NAME);",'"' + currDate + '"');
		ResultSet resultSet = statement.executeQuery(getString);
		List<Stamp> stamps = new LinkedList<>();
		String user, bathroom, date, timeDiff;
		while (resultSet.next()) {
			user = resultSet.getString(1);
			bathroom = resultSet.getString(2);
			date = resultSet.getString(3);
			timeDiff = resultSet.getString(4);
			
			Stamp stamp = new Stamp(user, bathroom, date, timeDiff);
			stamps.add(stamp);
		}
		return stamps;
		
	}
	public Stamp get_latest_stamp(String bathroom) throws SQLException{
		String currDate = Utils.getCurrTime();
		String getString = String.format(
				"SELECT USER_NAME, DATE, TIMEDIFF(%s,DATE)"
				+ "FROM stamps WHERE BATHROOM_NAME = %s ORDER BY ID DESC LIMIT 1;",'"' + currDate + '"', '"' + bathroom + '"');
		ResultSet resultSet = statement.executeQuery(getString);

		String user, date, timeDiff;
		user = resultSet.getString(1);
		date = resultSet.getString(2);
		timeDiff = resultSet.getString(3);
		Stamp stamp = new Stamp(user, bathroom, date, timeDiff);
	
		return stamp;
		
	}
	public int insert_user(String name) throws SQLException {
		String insertString = String.format(Locale.US,"INSERT INTO users (NAME) VALUES (%s)", name);
		return statement.executeUpdate(insertString);
	}
	
	public int insert_bathroom(String name) throws SQLException {
		String insertString = String.format(Locale.US,"INSERT INTO bathrooms (NAME) VALUES (%s)", name);
		return statement.executeUpdate(insertString);
	}

}
