package com.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.aspect.DbConnectionProvider;
import com.beans.Account;
import com.dao.AccountDAOInterface;

//CRUD operations implementation 

public class AccountDAOImpl implements AccountDAOInterface{
	
	//get database connection from the provider  class
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void setConnection(Connection con) {
		this.con=con;
	}

	
	public int insertRecord(Account a) {
		
		int updateCount=0;
		
		try
		{
			pst=con.prepareStatement("insert into Account values (?,?,?)");
			pst.setInt(1,a.getAccNo());
			pst.setString(2,a.getAccType());
			pst.setFloat(3,a.getAccBal());
			updateCount=pst.executeUpdate();
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return updateCount;
	}


	public Account retriveRecord(int accNo) {
		
		Account a=null;
		
		try
		{
			
			pst=con.prepareStatement("select * from Account where accNo=?");
			pst.setInt(1, accNo);
			rs=pst.executeQuery();
//			rs.next(); //becoz rs refaces to before first position // not use while bocoz Wahase 1 hi recored milne wala hai  //record milega tab ye kaam hoga if nahi mila tho
			
			if(rs.next())
			{
			int no=rs.getInt(1);
			String accType=rs.getString(2);
			float accBal=rs.getFloat(3);
			
			a=new Account(accType, accBal);
			a.setAccNo(no);  //Overlaid acc no set database se mila wala acc no, becoz har ek new account ke saath naya accno. genrate hota hai wo hame nahi chahiye
			}
			else {
				a=null; 
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return a;
	}



	public int deleteAccount(int accNo) {
	    int updateCount = 0;
	    
	    try {
	        pst = con.prepareStatement("delete from Account where accNo=?");
	        pst.setInt(1, accNo);
	        updateCount = pst.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return updateCount;
	}




	public int updateAccBal(int accNo, float newAccBal) {
	    int updateCount = 0;
	    
	    try {
	        pst = con.prepareStatement("update Account set accBal=? where accNo=?");
	        pst.setFloat(1, newAccBal);
	        pst.setInt(2, accNo);
	        updateCount = pst.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return updateCount;
	}
	
//	public int updateAccBal(Account a) {
//		int updateCount = 0;
//		
//		try {
//			pst = con.prepareStatement("update Account set accBal=? where accNo=?");
//			pst.setFloat(1,a.getAccBal());
//			pst.setInt(2,a.getAccNo());
//			updateCount = pst.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return updateCount;
//	}


}
