package com.aspect;

import java.io.FileInputStream;
import java.security.PublicKey;
import java.util.Properties;

import com.dao.AccountDAOInterface;
import com.dao.LoginDAOInterface;
import com.daoimpl.AccountDAOImpl;
import com.mysql.cj.protocol.a.result.ResultsetRowsStatic;

//services from Business Logic

import com.serviceImpl.AccountServiceImpl;
import com.services.AccountServices;
import com.services.LoginServices;

public class ObjectProvider {

	public static AccountServices createObje(String BClassName) {
		///////////////////////////////////  29/01/2025//////////////////////////////////////////
//		
//		FileInputStream fis=null;
		AccountServices acc=null;
		
		try {
			
//			//reading the middleware
//			fis=new FileInputStream(".//resources//info.properties");
//			Properties p=new Properties();
//			p.load(fis);
//			
//			String bClass=p.getProperty("BClassName");
			
			//build the object of BusinessLogic Class
			acc=(AccountServices)Class.forName(BClassName).newInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return acc;
	}
		////////////////////////////////////////////////////////////////////////////////////
		
		public static AccountDAOInterface createDAObject(String DAO){
			
			AccountDAOInterface a=null;
			try
			{
//				FileInputStream fis=null;
//				AccountServices acc=null;
//				
//				//reading the middalware
//				fis=new FileInputStream(".//resources//info.properties");
//				Properties p=new Properties();
//				p.load(fis);
//				String DAO=p.getProperty("DAOClass");
				
				//Create DAO object....
				a=(AccountDAOInterface) Class.forName(DAO).newInstance();  //downcasting in orignal type
				
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			return a;
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////
		
		public static LoginServices createLoginObje() {
	
			FileInputStream fis=null;
			LoginServices log=null;
			
			try {
				
				//reading the middleware
				fis=new FileInputStream(".//resources//info.properties");
				Properties p=new Properties();
				p.load(fis);
				
				String bClass=p.getProperty("BLoginClassName");
				
				//build the object of BusinessLogic Class
				log=(LoginServices)Class.forName(bClass).newInstance();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return log;
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////////
		public static LoginDAOInterface createLoginDAObject(){
			
			LoginDAOInterface l=null;
			try
			{
				FileInputStream fis=null;
				LoginServices log=null;
				
				//reading the middalware
				fis=new FileInputStream(".//resources//info.properties");
				Properties p=new Properties();
				p.load(fis);
				String DAO=p.getProperty("LoginDAOClass");
				
				//Create DAO object....
				l=(LoginDAOInterface) Class.forName(DAO).newInstance();
				
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			return l;
		}
		
}

