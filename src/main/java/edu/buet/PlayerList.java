package edu.buet;

import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class PlayerList {

    private List<Player> playerList = new ArrayList<Player>();
    private List<Club> clubList = new ArrayList<Club>();
    private List<Country> countryList = new ArrayList<Country>();

    public PlayerList() {}

    public List<Player> get() {
        return playerList;
    }

    public List<Club> getClubList() {
        return clubList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void add (Player p) {
        playerList.add(p);
    }

    public Player searchByName (String name) {
        for (Player p : playerList) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    public List<Player> searchByPosition (String position) {
        List<Player> posList = new ArrayList<Player>();
        for (Player p : playerList) {
            if (p.getPosition().equalsIgnoreCase(position)) posList.add(p);
        }
        if (posList.size() == 0) return null;
        return posList;
    }

    public List<Player> searchBySalary (double r1, double r2) {
        double l, h;
        if (r2 > r1) {
            l = r1;
            h = r2;
        } else {
            l = r2;
            h = r1;
        }
        List<Player> salList = new ArrayList<Player>();
        for (Player p : playerList) {
            if (p.getSalary() > l && p.getSalary() < h) salList.add(p);
        }
        if (salList.size() == 0) return null;
        return salList;
    }

    public boolean isNameValid (String name) {
        for (Player p : playerList) if (p.getName().equalsIgnoreCase(name)) return false;
        return true;
    }

    public HashMap<String, Integer> countryCount() {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (Country c : countryList) counts.put(c.getName(), c.getPlayers().size());
        return counts;
    }

    public Club getClub (String club) {
        for (Club c : clubList) if (c.getName().equalsIgnoreCase(club)) return c;
        return null;
    }

    public Country getCountry (String country) {
        for (Country c : countryList) if (c.getName().equalsIgnoreCase(country)) return c;
        return null;
    }

    public void readFromFile (List<Club> clubList, List<Country> countryList) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("players.txt"));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");
            Player p = new Player();
            p.setName(tokens[0]);

            Country checkCountry = getCountry(tokens[1]);
            if (checkCountry==null) {
                p.setCountry(new Country(tokens[1]));
                countryList.add(p.getCountry());
                p.getCountry().addPlayer(p);
            } else {
                p.setCountry(checkCountry);
                checkCountry.addPlayer(p);
            }
            p.setAge(Integer.parseInt(tokens[2]));
            p.setHeight(Double.parseDouble(tokens[3]));

            Club checkClub = getClub(tokens[4]);
            if (checkClub==null) {
                p.setClub(new Club(tokens[4]));
                clubList.add(p.getClub());
                p.getClub().addPlayer(p);
            } else {
                p.setClub(checkClub);
                checkClub.addPlayer(p);
            }
            p.setPosition(tokens[5]);
            p.setNumber(Integer.parseInt(tokens[6]));
            p.setSalary(Double.parseDouble(tokens[7]));
            playerList.add(p);
        }
        br.close();
    }

    public void saveToFile() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter("players.txt"));
        for (Player s : playerList) {
            bw.write(s.getName() + "," + s.getCountry() + "," + s.getAge() + "," + s.getHeight() + "," + s.getClub()
                    + "," + s.getPosition() + "," + s.getNumber() + "," + s.getSalary());
            bw.write("\n");
        }
        bw.close();
    }

    public static List<String> nameList (List<Player> playerList) {
        List<String> list = new ArrayList<>();
        for (Player p : playerList) list.add(p.getName());
        return list;
    }

    // public List<String> guessPlayer (String name) {
    //     List<String> guesses = new ArrayList<String>();
    //     for (Player p : playerList) if(StringDeviation.deviation(p.getName(), name) < 0.2) guesses.add(p.getName());
    //     if (guesses.size() < 1) return null;
    //     return guesses;
    // }

}
