package com.services;

import com.exception.InvalidAccountNoException;
import com.exception.LowBalanceException;

//Menu card of Business logic methods
public interface AccountServices {
	
	int OpenAccount(String accType,float amount);
	float deposit(int accNo,float amount) throws InvalidAccountNoException;
	float balEnquiry(int accNo) throws InvalidAccountNoException;
	float withdraw(float amount,int accNo) throws InvalidAccountNoException, LowBalanceException;
	public int deleteAcc(int accNo) throws InvalidAccountNoException;
	

}
