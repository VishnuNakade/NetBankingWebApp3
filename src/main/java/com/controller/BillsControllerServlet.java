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

@WebServlet("/BillsControllerServlet")
public class BillsControllerServlet extends HttpServlet {
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
        String action = request.getParameter("pageid");

        switch (action) {
            case "PPN":
                processPPN(request, response);
                break;
            case "BankTransfer":
                processBankTransfer(request, response);
                break;
            case "MobileRecharge":
                processMobileRecharge(request, response);
                break;
            case "QRPayment":
                processQRPayment(request, response);
                break;
            default:
                response.sendRedirect("Login.html?message=Invalid action.");
                break;
        }
    }

    private void processPPN(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String account = request.getParameter("account");
            String phone = request.getParameter("phone");
            String amount = request.getParameter("amount");
            HttpSession session = request.getSession(false);
            RequestDispatcher rd;

            if (isValidAccount(account)) {
                updateAccountBalance(account, Double.parseDouble(amount));
                if (session != null) {
                    session.setAttribute("message", "PPN Successful!");
                }
            } else {
                if (session != null) {
                    session.setAttribute("message", "Invalid account.");
                }
            }
            rd = request.getRequestDispatcher("/Outcome.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processBankTransfer(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String yourAccount = request.getParameter("your-account");
            String beneficiaryAccount = request.getParameter("beneficiary-account");
            String ifsc = request.getParameter("ifsc");
            double amount = Double.parseDouble(request.getParameter("amount"));
            HttpSession session = request.getSession(false);
            RequestDispatcher rd;

            if (isValidAccount(yourAccount) && isValidAccount(beneficiaryAccount)) {
                if (transferFunds(yourAccount, beneficiaryAccount, amount)) {
                    if (session != null) {
                        session.setAttribute("message", "Transfer Successful!");
                    }
                } else {
                    if (session != null) {
                        session.setAttribute("message", "Insufficient Balance!");
                    }
                }
            } else {
                if (session != null) {
                    session.setAttribute("message", "Invalid Account Details!");
                }
            }
            rd = request.getRequestDispatcher("/Outcome.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processMobileRecharge(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String account = request.getParameter("account");
            String mobile = request.getParameter("mobile");
            double amount = Double.parseDouble(request.getParameter("amount"));
            HttpSession session = request.getSession(false);
            RequestDispatcher rd;

            if (isValidAccount(account)) {
                if (deductBalance(account, amount)) {
                    if (session != null) {
                        session.setAttribute("message", "Recharge Successful!");
                    }
                } else {
                    if (session != null) {
                        session.setAttribute("message", "Insufficient Balance!");
                    }
                }
            } else {
                if (session != null) {
                    session.setAttribute("message", "Invalid Account!");
                }
            }
            rd = request.getRequestDispatcher("/Outcome.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private void processQRPayment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String yourAccount = request.getParameter("your-account");
            String beneficiaryAccount = request.getParameter("beneficiary-account");
            double amount = Double.parseDouble(request.getParameter("amount"));
            HttpSession session = request.getSession(false);
            RequestDispatcher rd;

            if (isValidAccount(yourAccount) && isValidAccount(beneficiaryAccount)) {
                boolean success = transferFunds(yourAccount, beneficiaryAccount, amount); // Calls improved method
                if (session != null) {
                    if (success) {
                        session.setAttribute("message", "Transfer Successful!");
                    } else {
                        session.setAttribute("message", "Insufficient Balance or Error in Transfer!");
                    }
                }
            } else {
                if (session != null) {
                    session.setAttribute("message", "Invalid Account Details!");
                }
            }
            rd = request.getRequestDispatcher("/Outcome.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean isValidAccount(String accountNo) {
        String query = "SELECT * FROM Account WHERE accNo = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, accountNo);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean transferFunds(String fromAccount, String toAccount, double amount) {
        try {
            con.setAutoCommit(false);
            if (!deductBalance(fromAccount, amount)) {
                con.rollback();
                return false;
            }
            updateAccountBalance(toAccount, amount);
            con.commit();
            return true;
        } catch (SQLException e) {
            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        }
    }

    private boolean deductBalance(String accountNo, double amount) throws SQLException {
        String query = "UPDATE Account SET accBal = accBal - ? WHERE accNo = ? AND accBal >= ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setDouble(1, amount);
            pst.setString(2, accountNo);
            pst.setDouble(3, amount);
            return pst.executeUpdate() > 0;
        }
    }

    private void updateAccountBalance(String accountNo, double amount) throws SQLException {
        String query = "UPDATE Account SET accBal = accBal + ? WHERE accNo = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setDouble(1, amount);
            pst.setString(2, accountNo);
            pst.executeUpdate();
        }
    }
}
