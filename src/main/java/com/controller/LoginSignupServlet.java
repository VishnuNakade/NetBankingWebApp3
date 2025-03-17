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
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

//@WebServlet("/AuthServlet")
public class LoginSignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection con;
    private DataSource dataSource;

    public void init() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDB");
            System.out.println("✅ Database connection initialized using JNDI.");
        } catch (NamingException e) {
            throw new RuntimeException("❌ Failed to initialize database connection: " + e.getMessage(), e);
        }
    }

    private Connection getConnection() throws SQLException {
        System.out.println("🔄 Fetching a new connection from the pool...");
        return dataSource.getConnection();
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
            PrintWriter out = response.getWriter();
            
            // Get login credentials from form
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            boolean validation = validateUser(username, password);

            if (validation) {
                // Login successful
                HttpSession session = request.getSession(true);
                RequestDispatcher rd = request.getRequestDispatcher("/index.html");
                rd.forward(request, response);
            } else {
                // Login failed
                RequestDispatcher rd = request.getRequestDispatcher("/Login.html");
                rd.include(request, response);
                out.println("Invalid username & password... Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSignup(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        
        // Generate a random 6-digit ID
        int id = (int) (Math.random() * 900000) + 100000;
        
        // Get data from signup form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String securityQuestion = request.getParameter("SecurityQuestion");
        String securityAnswer = request.getParameter("SecurityAnswer");

        // Insert new user
        int success = insertUser(id, username, password, securityQuestion, securityAnswer);
        if (success == 1) {
            // Redirect to login page on successful signup
            response.sendRedirect("Login.html?message=Signup successful! Redirecting to login...");
        } else {
            // Redirect back to signup page if user exists
            response.sendRedirect("Signup.html?message=Username already exists. Please try again.");
        }
    }

    private boolean validateUser(String username, String password) {
        String query = "SELECT * FROM login WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();  // Create a new connection
             PreparedStatement pst = conn.prepareStatement(query)) {

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
        try (Connection conn = getConnection();  // Create a new connection
             PreparedStatement pst = conn.prepareStatement(query)) {

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
