package com.beans;

public class Login {
    
    private int id;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    
    private Integer accNo;
    
    // Constructor
    public Login(int id, String password, String securityQuestion, String securityAnswer) {
        this.id = id;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.accNo=null;
    }

    // Default constructor
    public Login() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
    
    public Integer getAccNo() {
		return accNo;
	}

	public void setAccNo(Integer accNo) {
		this.accNo = accNo;
	}

	// toString method
    @Override
    public String toString() {
        return "Login [id=" + id + ", password=" + password + ", securityQuestion=" + securityQuestion + ", securityAnswer=" + securityAnswer + ", accNo=" + accNo + "]";
    }
}
