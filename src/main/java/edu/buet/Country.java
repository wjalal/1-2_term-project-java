package edu.buet;

import java.util.*;
import javafx.scene.image.*;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.nio.file.*;

public class Country extends Team {

    private byte[] flagBytes;
    private int countfromclub;

    public Country() {
        super();
    }

    public Country(String name) throws Exception {
        super(name);
        this.flagBytes = Files.readAllBytes(Paths.get("cflag/" + this.getName() + ".png"));
    }

    public List<Player> searchByClubAndCountry (Club club) {
        List<Player> countryList = new ArrayList<Player>();
        for (Player p : this.getPlayers()) if (p.getClub() == club) countryList.add(p);
        if (countryList.size() < 1) return null;
        return countryList;
    }

    public List<Player> searchByClubAndCountry() {
        List<Player> countryList = new ArrayList<Player>();
        for (Player p : this.getPlayers()) countryList.add(p);
        if (countryList.size() < 1) return null;
        return countryList;
    }


    public static List<String> nameList (List<Country> teamList) {
        List<String> list = new ArrayList<>();
        for (Country p : teamList) list.add(p.getName());
        return list;
    }

    public Image getFlag() {
        InputStream is = new ByteArrayInputStream(flagBytes); 
        Image flag = new Image(is);
        return flag;
    }

    public int getCountFromClub (Club club) {
        int count = 0;
        for (Player p : club.getPlayers()) if (p.getCountry() == this) count ++;
        return count;
    }

    public int getCountfromclub() {
        return countfromclub;
    }

    public void setCountfromclub(int countfromclub) {
        this.countfromclub = countfromclub;
    }

    public void setFlagBytes(byte[] flagBytes) {
        this.flagBytes = flagBytes;
    }

    public byte[] getFlagBytes() {
        return flagBytes;
    }
}