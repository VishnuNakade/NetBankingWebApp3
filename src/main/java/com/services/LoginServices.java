package com.services;

import com.beans.Login;
import com.exception.InvalidCredentialsException;

public interface LoginServices {
    
    // Method to sign up a new user
    int signUp(int id, String password, String sQue, String sAns);

    // Method to sign in a user
    boolean signIn(int id, String password) throws InvalidCredentialsException;

    // Method to reset password using security question
    public boolean forgetPassword(int id, String userAnswer, String newPassword);

    // Method to update password (after logging in)
    boolean updatePassword(int id, String oldPassword, String newPassword)throws InvalidCredentialsException;

    // Method to deregister (delete account)
    boolean deregister(int id) throws InvalidCredentialsException;

	String getSecurityQuestionById(int id);
    
   
}

