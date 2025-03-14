package com.daoimpl;

import com.aspect.DbConnectionProvider;
import com.beans.Login;
import com.dao.LoginDAOInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOImpl implements LoginDAOInterface {

    private Connection con; // Database connection
    private PreparedStatement pst;
    private ResultSet rs;

    // Default constructor (fix for InstantiationException)
    public LoginDAOImpl() {
        this.con = DbConnectionProvider.ProvideConnection();
        if (this.con == null) {
            throw new IllegalStateException("Database connection is null!");
        }
    }

    @Override
    public int insertUser(Login login) {
        String query = "INSERT INTO login (id, password, security_question, security_answer) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, login.getId());
            pst.setString(2, login.getPassword());
            pst.setString(3, login.getSecurityQuestion());
            pst.setString(4, login.getSecurityAnswer());
            return pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Login getUserById(int id) {
        String query = "SELECT * FROM login WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Login(
                    rs.getInt("id"),
                    rs.getString("password"),
                    rs.getString("security_question"),
                    rs.getString("security_answer")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean validateUser(int id, String password) {
        String query = "SELECT * FROM login WHERE id = ? AND password = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updatePassword(int id, String oldPassword, String newPassword) {
        String query = "UPDATE login SET password = ? WHERE id = ? AND password = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, newPassword);
            pst.setInt(2, id);
            pst.setString(3, oldPassword);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean resetPassword(int id, String sQue, String sAns, String newPassword) {
        String checkQuery = "SELECT * FROM login WHERE id = ? AND security_question = ? AND security_answer = ?";
        String updateQuery = "UPDATE login SET password = ? WHERE id = ?";
        try (PreparedStatement checkPs = con.prepareStatement(checkQuery)) {
            checkPs.setInt(1, id);
            checkPs.setString(2, sQue);
            checkPs.setString(3, sAns);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next()) {
                try (PreparedStatement updatePs = con.prepareStatement(updateQuery)) {
                    updatePs.setString(1, newPassword);
                    updatePs.setInt(2, id);
                    return updatePs.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(int id) {
        String query = "DELETE FROM login WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public String getSecurityQuestionById(int id) {
        String query = "SELECT security_question FROM login WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("security_question"); // Return security question
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if the user is not found
    }

    @Override
    public boolean verifySecurityAnswer(int id, String userAnswer) {
        String query = "SELECT security_answer FROM login WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedAnswer = rs.getString("security_answer");
                return storedAnswer.equalsIgnoreCase(userAnswer); // Case-insensitive match
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // If user not found or answer is incorrect
    }

    @Override
    public boolean updatePassword(int id, String newPassword) {
        String query = "UPDATE login SET password = ? WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, newPassword);
            pst.setInt(2, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int updateUserAccountNo(int id, int accNo) {
        String query = "UPDATE login SET accNo = ? WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, accNo);
            pst.setInt(2, id);
            int rowsAffected = pst.executeUpdate(); // Execute the update
            return rowsAffected; // Return the number of affected rows
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Indicate failure
        }
    }
    
    
    @Override
    public int getAccTroughID(int userId) {
        String query = "SELECT accNo FROM login WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int storedAnswer = rs.getInt("accNo");
                return storedAnswer; // Case-insensitive match
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // If user not found or answer is incorrect
    }


    
}
