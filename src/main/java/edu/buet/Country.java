package edu.buet;

import java.util.*;
import javafx.scene.image.*;

public class Country extends Team {

    private Image flag;

    public Country() {
        super();
    }

    public Country(String name) {
        super(name);
        this.flag = new Image(getClass().getResourceAsStream("cflag/" + this.getName() + ".png"));
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
        return this.flag;
    }
}