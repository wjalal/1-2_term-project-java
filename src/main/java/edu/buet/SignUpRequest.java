package edu.buet;

import java.io.Serializable;

public class SignUpRequest implements Serializable {
    
    private LoginCredential credential;
    private byte[] logoBytes;

    public SignUpRequest() {

    }

    public SignUpRequest (LoginCredential credential, byte[] logoBytes) {
        this.credential = credential;
        this.logoBytes = logoBytes;
    }

    public LoginCredential getCredential() {
        return credential;
    }

    public byte[] getLogoBytes() {
        return logoBytes;
    }

    public void setCredential(LoginCredential credential) {
        this.credential = credential;
    }

    public void setLogoBytes(byte[] logoBytes) {
        this.logoBytes = logoBytes;
    }
    
}
