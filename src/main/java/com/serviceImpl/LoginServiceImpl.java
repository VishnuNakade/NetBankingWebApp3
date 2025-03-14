package com.serviceImpl;

import com.aspect.ObjectProvider;
import com.beans.Login;
import com.dao.LoginDAOInterface;
import com.exception.InvalidCredentialsException;
import com.services.LoginServices;

public class LoginServiceImpl implements LoginServices {
    
    Login l;
    LoginDAOInterface obj = ObjectProvider.createLoginDAObject();
    
    /////////////////// Sign Up //////////////////////
    
    public int signUp(int id, String password, String sQue, String sAns) {
        l = new Login(id, password, sQue, sAns);
        
        int res = obj.insertUser(l);
        if (res > 0) {
            System.out.println("BL: Login Account created successfully with ID: " + id);
            System.out.println("DB: Data inserted into database successfully");
        } else {
            System.out.println("DB: Unable to store data in the database");
        }
        
        return id;
    }
    
    /////////////////// Sign In //////////////////////
    
    public boolean signIn(int id, String password) throws InvalidCredentialsException {
        l = obj.getUserById(id);
        
        if (l != null && l.getPassword().equals(password)) {
            System.out.println("BL: SignIn successful");
            return true;
        } else {
            throw new InvalidCredentialsException("BL: Invalid credentials. Please try again.");
        }
    }
    
    /////////////////// Reset Password //////////////////////
    
    public boolean forgetPassword(int id, String userAnswer, String newPassword) {
        // Step 1: Fetch the security question
        String securityQuestion = obj.getSecurityQuestionById(id);

        if (securityQuestion == null) {
            System.out.println("User ID not found!");
            return false;
        }

        // Step 2: Display the security question
        System.out.println("Security Question: " + securityQuestion);

        // Step 3: Verify the security answer
        if (!obj.verifySecurityAnswer(id, userAnswer)) {
            System.out.println("Incorrect security answer! Password reset failed.");
            return false;
        }

        // Step 4: Update password
        if (obj.updatePassword(id, newPassword)) {
            System.out.println("Password reset successful!");
            return true;
        } else {
            System.out.println("Error resetting password.");
            return false;
        }
    }


    
    /////////////////// Update Password //////////////////////
    
    public boolean updatePassword(int id, String oldPassword, String newPassword) throws InvalidCredentialsException {
        l = obj.getUserById(id);
        
        if (l != null && l.getPassword().equals(oldPassword)) {
            boolean isUpdated = obj.updatePassword(id, oldPassword, newPassword);
            if (isUpdated) {
                System.out.println("BL: Password updated successfully.");
                return true;
            } else {
                System.out.println("DB: Failed to update password.");
            }
        } else {
            throw new InvalidCredentialsException("BL: Incorrect old password. Please try again.");
        }
        return false;
    }
    
    /////////////////// Delete Account //////////////////////
    
    public boolean deregister(int id) throws InvalidCredentialsException {
        l = obj.getUserById(id);
        
        if (l != null) {
            boolean isDeleted = obj.deleteUser(id);
            if (isDeleted) {
                System.out.println("BL: Login Account deleted successfully.");
                return true;
            } else {
                System.out.println("DB: Failed to delete account.");
            }
        } else {
            throw new InvalidCredentialsException("BL: Invalid Account ID. Account not found.");
        }
        return false;
    }

    @Override
    public String getSecurityQuestionById(int id) {
        return obj.getSecurityQuestionById(id);
    }

}