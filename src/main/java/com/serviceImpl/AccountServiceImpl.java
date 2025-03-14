 package com.serviceImpl;

import java.sql.Connection;

import com.aspect.ObjectProvider;
import com.beans.Account;
import com.exception.InvalidAccountNoException;
import com.exception.LowBalanceException;
import com.services.AccountServices;
import com.dao.AccountDAOInterface;
import com.daoimpl.AccountDAOImpl;

public class AccountServiceImpl implements AccountServices {

	//given data to business logic------has a relation
	Account a;
	
	//Interaction with DAO Layer
	AccountDAOInterface dao;
	Connection con;
	
	public void setConfigInfo(String DAO,Connection con) {
		this.con=con;
		dao=ObjectProvider.createDAObject(DAO);
		((AccountDAOImpl)dao).setConnection(con);
		
	}

	//Interaction with DAO Layer
//	AccountDAOInterface obj= ObjectProvider.createDAObject();


	//////////////////OpenAccount////////////////////

	public int OpenAccount(String accType, float amount) 
	{
		a=new Account(accType,amount);
		//		assert a.getAccBal() == amount : "Account balance mismatch after opening account. Expected: " + amount + ", Actual: " + a.getAccBal();

		////////DAO/////////
		int res=dao.insertRecord(a); //Persist(store) data in DB-----insert 
		if(res>0) 
		{	System.out.println("Account created succesfuly....with accNo "+a.getAccNo());
		System.out.println("DB: Data insart in database Sussefully");
		}
		else 
		{
			System.out.println("DB: unable to store data in databsae");
		}
		/////////////////

		return a.getAccNo();	
	}


	///////////////////////balEnquir/////////////////////////

	public float balEnquiry(int accNo) throws InvalidAccountNoException 
	{

		a=dao.retriveRecord(accNo);  //DAO //Extract record from DB   //(self) obj.retriveRecord(accNo) it's return account store in DB ,we store that in BL account a here

		float bal=0.0f;   //BL
		if(a.getAccNo()==accNo)
		{
			bal=a.getAccBal();
		}
		else
		{
			System.out.println("BL: Invalid Account Number please try again ");
		}

		return bal;
	}


	///////////////////////deposit/////////////////////////

	public float deposit(int accNo, float amount) throws InvalidAccountNoException
	{
		float bal=0.0f;

		a=dao.retriveRecord(accNo);  //DAO ///Extract record from DB

		//if(a.getAccNo()==accNo) { //BL

		if(a!=null)
		{ //BL
			bal=a.getAccBal()+amount;
			a.setAccBal(bal); //local obj		
			
			// a.setAccBal(8000);  //for assertion checking, if we set wrong bal
			assert a.getAccBal() == bal : "BL: Balance mismatch after deposit. Expected: " + bal + ", Actual: " + a.getAccBal();
		}
		else 
		{
			throw new InvalidAccountNoException("BL: Account no. is not valid......");
		}

		/////////DAO////
//		int res=obj.updateAccBal(a);
		int res=dao.updateAccBal(accNo, bal);   //update the account balance in DB
		if(res>0) 
		{
			System.out.println("DB: Update AccBal Sussefully");
		}
		else 
		{
			System.out.println("DB: unable to Update AccBal");
		}
		/////////

		return bal;
	}


	///////////////////////withdraw/////////////////////////

	public float withdraw(float amount,int accNo) throws InvalidAccountNoException, LowBalanceException 
	{
		float bal = 0.0f;

		a=dao.retriveRecord(accNo);  //DAO //Extract record from DB

		if (a.getAccNo() == accNo) //BL
		{
			if (amount <= 0)
			{
				System.out.println("BL: Invalid withdrawal amount.");
				return -1.0f;
			}
			if (a.getAccBal() < amount) 
			{
				throw new LowBalanceException("BL: Your balance is to low");
				//                return -1.0f;
			}
			// Deduct the withdrawal amount
			bal = a.getAccBal() - amount;
			a.setAccBal(bal);

			//            a.setAccBal(8000);  //for assertion checking, if we set wrong bal
			assert bal == a.getAccBal() : "BL: Balance mismatch after withdrawal. Expected: " + bal + ", Actual: " + a.getAccBal();

			System.out.println("BL: Withdrawal successful. Remaining balance: " + bal);
		} 

		else 
		{
			throw new InvalidAccountNoException("BL: Account no. is not valid...");
		}


		//////DAO////////
		int res=dao.updateAccBal(accNo, bal);
		if(res>0) 
		{
			System.out.println("DB: Update AccBal Sussefully");
		}
		else 
		{
			System.out.println("DB: unable to Update AccBal");
		}
		//////////////

		return bal;
	}


	///////////////////////deleteAccount/////////////////////////

	public int deleteAcc(int accNo) throws InvalidAccountNoException 
	{

		a=dao.retriveRecord(accNo);  //DAO //Extract record from DB


		if(a.getAccNo()==accNo) //BL
		{
			System.out.println("BL: Account with account No.: "+accNo+" "+"deleted");
		}
		else
		{
			System.out.println("BL: Invalid Account Number please try again ");
		}

		/////////DAO////
		int res=dao.deleteAccount(accNo);
		if(res>0) 
		{
			System.out.println("DB: delete Sussefull");
		}
		else 
		{
			System.out.println("DB: delete unsussefull");
		}
		/////////

		return 0;
	}


}
