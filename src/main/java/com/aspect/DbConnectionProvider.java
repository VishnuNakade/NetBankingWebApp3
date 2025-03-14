package com.aspect;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbConnectionProvider {

	
	public static Connection ProvideConnection() {
		
		Connection con=null;
		//Read middleware
		try 
		{
			FileInputStream fis=new FileInputStream(".//Resources//DbInfo.properties");
			Properties p=new Properties();
			p.load(fis);
			String driverClass=p.getProperty("driver");
			String url=p.getProperty("url");
			String username=p.getProperty("username");
			String password=p.getProperty("password");
			
			//1.load driver class into memory 
			Class.forName(driverClass);
			//2. Establish connection
			con = DriverManager.getConnection(url, username,password);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return con;
		
	}
	
	

}
