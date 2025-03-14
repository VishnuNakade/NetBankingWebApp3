package com.dao;

import com.beans.Account;

//CRUD operations summary
public interface AccountDAOInterface {
	
	public int insertRecord(Account a);
	public Account retriveRecord(int accNo);
	public int updateAccBal(int accNo,float newAccBal);
	public int deleteAccount(int accNo); 
}
