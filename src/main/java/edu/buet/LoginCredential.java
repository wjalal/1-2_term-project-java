package edu.buet;

import java.io.Serializable;

public class LoginCredential implements Serializable {
    private String name;
    private String passwordHash;

    public LoginCredential() {

    }

    public LoginCredential (String name, String password) {
        this.name = name;
        this.setPassword(password);;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.passwordHash = SHA512.generateSHA512(password);
    }
    
}
