package edu.buet;
import java.io.Serializable;

public class ThemeUpdateRequest implements Serializable {

    private Club club;
    private String theme;

    public ThemeUpdateRequest() {

    }

    public ThemeUpdateRequest (Club club, String theme) {
        this.club = club;
        this.theme = theme;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Club getClub() {
        return club;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

}