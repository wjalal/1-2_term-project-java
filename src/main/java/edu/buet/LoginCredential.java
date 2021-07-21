package edu.buet;

import java.io.Serializable;

public class LoginCredential implements Serializable {
    private String name;
    private String password;

    public LoginCredential() {

    }

    public LoginCredential (String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
