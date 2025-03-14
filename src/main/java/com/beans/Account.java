package com.beans;

//Data manager-------Java Bean
public class Account {

	//Data abstraction and encapsulation
	
	private int accNo;
	private String accType;
	private float accBal;
	private static int series=77777;
	
	
	public Account(String accType, float accBal) {
//		this.accNo = series;
		this.accNo = 111 + (int) (Math.random() * 1000); ;
		series++;
		this.accType = accType;
		this.accBal = accBal;
	}


	public int getAccNo() {
		return accNo;
	}


	public void setAccNo(int accNo) {
		this.accNo = accNo;
	}


	public String getAccType() {
		return accType;
	}


	public void setAccType(String accType) {
		this.accType = accType;
	}


	public float getAccBal() {
		return accBal;
	}


	public void setAccBal(float accBal) {
		this.accBal = accBal;
	}


	public static int getSeries() {
		return series;
	}


	public static void setSeries(int series) {
		Account.series = series;
	}


	@Override
	public String toString() {
		return "Account [accNo=" + accNo + ", accType=" + accType + ", accBal=" + accBal + "]";
	}
	
	
}
