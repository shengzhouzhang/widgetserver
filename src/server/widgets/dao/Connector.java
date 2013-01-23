package server.widgets.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {

	static public Connection getConnction() throws SQLException, ClassNotFoundException{
		
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost/widgets";
		Properties props = new Properties();
		props.setProperty("user","widgetserver");
		props.setProperty("password","widgetserver");
		return DriverManager.getConnection(url, props);
	}
}
