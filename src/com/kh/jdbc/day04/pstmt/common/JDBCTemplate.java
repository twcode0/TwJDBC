package com.kh.jdbc.day04.pstmt.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate {
	private static String driverName;
//	private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
//	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
//	private static final String USERNAME = "TWJDBC";
//	private static final String PASSWORD = "TWJDBC";
	
//	public JDBCTemplate() {
//		try {
//			prop = new Properties();
//			prop.load(new FileReader("resources/dev.properties"));
//			driverName = prop.getProperty("driverName");
//			url = prop.getProperty("url");
//			user = prop.getProperty("user");
//			password = prop.getProperty("password");
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	private static final String FILE_NAME = "resources/dev.properties";
	private static Properties prop;
	private static Connection conn;
	
	public static Connection getConnection()
							throws ClassNotFoundException, SQLException, IOException {
		prop = new Properties();
		Reader reader = new FileReader(FILE_NAME);
		prop.load(reader);
		String driverName = prop.getProperty("driverName");
		String url = prop.getProperty("url");
		String user = prop.getProperty("user");
		String password = prop.getProperty("password");
		if(conn == null || conn.isClosed()) {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);
		}
		return conn;
	}
	
}
