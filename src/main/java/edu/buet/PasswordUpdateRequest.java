package edu.buet;

import java.io.Serializable;

public class PasswordUpdateRequest implements Serializable {

    private String oldPwdHash, newPwdHash;
    private Club club;

    public PasswordUpdateRequest() {

    }

    public PasswordUpdateRequest (String oldPwd, String newPwd, Club club) {
        this.setOldPwdHash(oldPwd);
        this.setNewPwdHash(newPwd);
        this.club = club;
    }

    public String getOldPwdHash() {
        return oldPwdHash;
    }

    public String getNewPwdHash() {
        return newPwdHash;
    }

    public void setOldPwdHash (String password) {
        this.oldPwdHash = SHA512.generateSHA512(password);
    }

    public void setNewPwdHash (String password) {
        this.newPwdHash = SHA512.generateSHA512(password);
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
    
}
