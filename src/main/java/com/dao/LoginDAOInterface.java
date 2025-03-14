package com.dao;

import com.beans.Login;

public interface LoginDAOInterface {
    
    int insertUser(Login login); // Sign Up
    Login getUserById(int id); // Retrieve user details
    boolean validateUser(int id, String password); // Sign In
    boolean updatePassword(int id, String oldPassword, String newPassword); // Change Password
    boolean resetPassword(int id, String sQue, String sAns, String newPassword); // Forgot Password
    boolean deleteUser(int id); // Deregister user
    String getSecurityQuestionById(int id);
    boolean verifySecurityAnswer(int id, String userAnswer); // Verify answer
    boolean updatePassword(int id, String newPassword);
    public int updateUserAccountNo(int id, int accNo);
    public int getAccTroughID(int userId);

    


}

