package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebServlet("/AuthServlet")
public class LoginSignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection con;

    public void init() {
        try {
            String driver = getServletContext().getInitParameter("driver");
            String url = getServletContext().getInitParameter("url");
            String username = getServletContext().getInitParameter("username");
            String password = getServletContext().getInitParameter("password");

            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Database connected successfully!");
        } catch (Exception e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            handleLogin(request, response);
        } else if ("signup".equals(action)) {
            handleSignup(request, response);
        } else {
            response.sendRedirect("Login.html?message=Invalid action.");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			PrintWriter out=response.getWriter();
			
			////////////////////Get data from  login.html page/////////////////////
			
			String username =request.getParameter("username");
			String password =request.getParameter("password");
			
			boolean validation=validateUser(username,password);
			
//			if(username.equals("java")&&password.equals("java")) hard coded values
			
			if(validation) {
//				out.println("Valid User");
				HttpSession session=request.getSession(true);
				RequestDispatcher rd=request.getRequestDispatcher("/index.html");
				rd.forward(request, response);
			}
			else 
			{
				RequestDispatcher rd=request.getRequestDispatcher("/Login.html");
				rd.include(request, response);
				
				out.println("Invalid username & password...plz try again....");
			}
			/////////////////////////////////////////////////////////////////////////
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
    }

    private void handleSignup(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        
     // Generate a random 6-digit ID
        int id = (int) (Math.random() * 900000) + 100000;
        
        // Get Data from Signup Form
//        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String SecurityQuestion = request.getParameter("SecurityQuestion");
        String SecurityAnswer = request.getParameter("SecurityAnswer");

        // ✅ Insert new user
        int success = insertUser(id,username, password, SecurityQuestion, SecurityAnswer);
        if (success == 1) {
			RequestDispatcher rd=request.getRequestDispatcher("/Login.html");
			rd.forward(request, response);
            // ✅ Pass success message to Signup.html
            response.sendRedirect("Signup.html?message=Signup successful! Redirecting to login...");
        } else {
//			RequestDispatcher rd=request.getRequestDispatcher("/Signup.html");
//			rd.forward(request, response);
            response.sendRedirect("Signup.html?message=Username already exists. Please try again.");
        }
    }

    private boolean validateUser(String username, String password) {
        String query = "SELECT * FROM login WHERE username = ? AND password = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int insertUser(int id, String username, String password, String securityQuestion, String securityAnswer) {
        String query = "INSERT INTO login (id, username, password, security_question, security_answer) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.setString(4, securityQuestion);
            pst.setString(5, securityAnswer);
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error inserting user: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}
