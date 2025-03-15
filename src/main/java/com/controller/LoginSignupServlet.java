package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/LoginSignupServlet")
public class LoginSignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void init() {
        try {
            System.out.println("🚀 Servlet initialized.");
            String driver = getServletContext().getInitParameter("driver");
            Class.forName(driver); // Ensure driver is loaded
        } catch (Exception e) {
            System.err.println("❌ Database driver load failed: " + e.getMessage());
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
            PrintWriter out = response.getWriter();
            
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            boolean validation = validateUser(username, password);

            if (validation) {
                HttpSession session = request.getSession(true);
                RequestDispatcher rd = request.getRequestDispatcher("/index.html");
                rd.forward(request, response);
            } else {
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
        
        int id = (int) (Math.random() * 900000) + 100000;
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String securityQuestion = request.getParameter("SecurityQuestion");
        String securityAnswer = request.getParameter("SecurityAnswer");

        int success = insertUser(id, username, password, securityQuestion, securityAnswer);
        if (success == 1) {
            response.sendRedirect("Login.html?message=Signup successful! Redirecting to login...");
        } else {
            response.sendRedirect("Signup.html?message=Username already exists. Please try again.");
        }
    }

    private boolean validateUser(String username, String password) {
        String query = "SELECT * FROM login WHERE username = ? AND password = ?";
        try (Connection conn = getConnection(); 
             PreparedStatement pst = conn.prepareStatement(query)) {
             
            pst.setString(1, username);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int insertUser(int id, String username, String password, String securityQuestion, String securityAnswer) {
        String query = "INSERT INTO login (id, username, password, security_question, security_answer) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); 
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

    private Connection getConnection() throws SQLException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDB");
            return ds.getConnection();
        } catch (NamingException e) {
            throw new SQLException("❌ JNDI lookup failed: " + e.getMessage());
        }
    }

}
