package com.exception;

public class LowBalanceException extends Exception{
	
	private String msg;

	public LowBalanceException(String msg) {
		this.msg = msg;
	}

	
	public String toString() {
		return "Problem is......."+msg;
	}
	
	

}
