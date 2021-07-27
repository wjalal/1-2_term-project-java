package edu.buet;
import java.util.*;
import javafx.scene.image.*;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.nio.file.*;

public class Club extends Team {

    private byte[] logoBytes;
    private String passwordHash;
    private double balance;
    private String theme = "Dark";

    public Club() {
        super();
    }

    public Club(String name) throws Exception {
        super(name);
        this.logoBytes = Files.readAllBytes(Paths.get("clublogo/" + this.getName() + ".png"));
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isFull() {
        if (this.getPlayers().size() < 7) return false;
        return true;
    }

    public boolean isNumberValid (int number) {
        for (Player p : this.getPlayers()) if ( p.getNumber()==number ) return false;
        return true;
    }

    public List<Player> searchByClubAndCountry (Country country) {
        List<Player> clubList = new ArrayList<Player>();
        for (Player p : this.getPlayers()) if (p.getCountry() == country) clubList.add(p);
        if (clubList.size() < 1) return null;
        return clubList;
    }

    public List<Player> searchByClubAndCountry() {
        List<Player> clubList = new ArrayList<Player>();
        for (Player p : this.getPlayers()) clubList.add(p);
        if (clubList.size() < 1) return null;
        return clubList;
    }

    public static List<String> nameList (List<Club> teamList) {
        List<String> list = new ArrayList<>();
        for (Club p : teamList) list.add(p.getName());
        return list;
    }

    public Image getLogo() {
        InputStream is = new ByteArrayInputStream(logoBytes); 
        Image logo = new Image(is);
        return logo;
    }

    public void setLogoBytes(byte[] logoBytes) {
        this.logoBytes = logoBytes;
    }

    public byte[] getLogoBytes() {
        return logoBytes;
    }

    public boolean containsCountry (Country country) {
        for (Player p : this.getPlayers()) if (p.getCountry() == country) return true;
        return false;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

}