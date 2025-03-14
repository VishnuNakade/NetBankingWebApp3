package com.controller;

import java.io.IOException;
import com.aspect.ObjectProvider;
import com.services.AccountServices;
import com.serviceImpl.AccountServiceImpl;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//1. Decision Making
//Identify the page who is sending req----red---to Baseness Logic comp. 

//@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	
	AccountServices acc;
	String BClassName;
	String DAOClass;
	Connection con;
	
	public void init() {
		
		//read the middleware i.e. wb.xml and get info
		BClassName=getServletConfig().getInitParameter("BClassName");
		DAOClass=getServletConfig().getInitParameter("DAOClass");
		
		//read the middleware i.e. web.xml file to getDB config info
        String driver = getServletContext().getInitParameter("driver");
        String url = getServletContext().getInitParameter("url");
        String username = getServletContext().getInitParameter("username");
        String password = getServletContext().getInitParameter("password");
        
        try {
        	Class.forName(driver);
        	con=DriverManager.getConnection(url,username,password);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try 
		{
			//Business Logic Class Object
			acc = ObjectProvider.createObje(BClassName);
			
			//Info about DAO to BusinessLogic class
			((AccountServiceImpl)acc).setConfigInfo(DAOClass,con);
			
			PrintWriter out=response.getWriter(); //for give some message 
			
			//page identification Logic.....
			String id = request.getParameter("pageid"); //pageid defined in html form  //input type hidden in html
			
			//for sending response back to client
			RequestDispatcher rd=null;
			
			//Http session tracking
			HttpSession session = request.getSession(false);   
			
			
			
	        if (id.equals("OA")) {
	        		
	        	//get req data from html page
	        	
				String type =request.getParameter("type");
				int amount=Integer.parseInt(request.getParameter("amount"));
	        	
	            int accNo=acc.OpenAccount(type, amount);
	            
//	            rd=request.getRequestDispatcher("/Outcome.html");
//	            rd.include(request, response);   //include becoz our html page is static so first include then write message eg. out.println
//	            out.print("Account Opened Succesfuly with AccNo"+accNo);
	            
	            
//	            HttpSession session = request.getSession(false);
	            if (session != null) { 
//	                session.removeAttribute("message"); //clear previous value //this part write in Outcome.js is better 
	                session.setAttribute("message", "Account Opened Successfully with AccNo " + accNo);
	            }
	            rd=request.getRequestDispatcher("/Outcome.jsp");
	            rd.forward(request, response); 


	        }
	        
	        
	        if (id.equals("BE"))
	        {
	        	int accNo=Integer.parseInt(request.getParameter("accountNumber"));
	        	
	        	float accBal=acc.balEnquiry(accNo);
	        	
//	            rd=request.getRequestDispatcher("/Outcome.html");
//	            rd.include(request, response);
//	        	out.print("Current Account Balance is..."+accBal);
	        	
 
	            if (session != null) { 
//	                session.removeAttribute("message"); //clear previous value
	                session.setAttribute("message", "Current Account Balance is..."+accBal);
	            }
	        	rd=request.getRequestDispatcher("/Outcome.jsp");
	            rd.forward(request, response);
	        }
	        
	        
	        if (id.equals("DA")) {
        		
	        	//get req data from html page
	        	int accNo=Integer.parseInt(request.getParameter("accountNumber"));
				int amount=Integer.parseInt(request.getParameter("amount"));
	        	
	            float dAmount=acc.deposit(accNo,amount);
	            
//	            rd=request.getRequestDispatcher("/Outcome.html");
//	            rd.include(request, response);
//	            out.print("upadated balance is(after deposit):"+dAmount);
	            

	            if (session != null) { 
//	                session.removeAttribute("message"); //clear previous value
	                session.setAttribute("message", "upadated balance is(after deposit):"+dAmount);
	            }
	        	rd=request.getRequestDispatcher("/Outcome.jsp");
	            rd.forward(request, response); 
	        }
	        
	        
	        if (id.equals("WA")) {
        		
	        	//get req data from html page
	        	int accNo=Integer.parseInt(request.getParameter("accountNumber"));
				int amount=Integer.parseInt(request.getParameter("amount"));
	        	
	            float wAmount=acc.withdraw(amount, accNo);
	            
//	            rd=request.getRequestDispatcher("/Outcome.html");
//	            rd.include(request, response);
//	            out.print("upadated balance is(after withdraw):"+wAmount);
	            

	            if (session != null) { 
//	                session.removeAttribute("message"); //clear previous value
	                session.setAttribute("message", "upadated balance is(after withdraw):"+wAmount);
	            }
	        	rd=request.getRequestDispatcher("/Outcome.jsp");
	            rd.forward(request, response); 
	        }
	        
	        
	        
	        if (id.equals("RA"))
	        {
	        	int accNo=Integer.parseInt(request.getParameter("accountNumber"));
	        	
	        	int res=acc.deleteAcc(accNo);
	        	
//	            rd=request.getRequestDispatcher("/Outcome.html");
//	            rd.include(request, response);
//	        	out.print("Account with account No.:"+accNo+"deleted");
	        	

	            if (session != null) { 
//	                session.removeAttribute("message"); //clear previous value
	                session.setAttribute("message", "Account with account No.: "+accNo+" deleted");
	            }
	        	rd=request.getRequestDispatcher("/Outcome.jsp");
	            rd.forward(request, response); 
	        	
	        }
	        
	        
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
