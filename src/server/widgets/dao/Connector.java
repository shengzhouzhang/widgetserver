package server.widgets.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** 
 *  This is the class to get connection of database.
 *  
 *  Database: postgresql
 *  Username: widgetserver
 *  Password: widgetserver
 *  Connection String: jdbc:postgresql://localhost/widgets
 *
 * @author Steven Zhang
 * @version 1.0 February 24, 2013.
 */
public class Connector {

	/**
	 * This method connects to postgresql database and returns its connection.
	 *
	 * @return the Connection of database
	 */
	static public Connection getConnction() throws SQLException, ClassNotFoundException{
		
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost/widgets";
		Properties props = new Properties();
		props.setProperty("user","widgetserver");
		props.setProperty("password","widgetserver");
		return DriverManager.getConnection(url, props);
	}
}
