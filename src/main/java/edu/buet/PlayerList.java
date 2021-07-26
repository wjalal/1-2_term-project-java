package edu.buet;

import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.File;
import java.nio.file.*;

public class PlayerList implements Serializable {

    private List<Player> playerList = new ArrayList<Player>();
    private List<Club> clubList = new ArrayList<Club>();
    private List<Country> countryList = new ArrayList<Country>();
    private Club clientClub = new Club();
    private List<Player> auctionList = new ArrayList<Player>();

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

    public List<Country> getCountryList (Club club) {
        List<Country> clubCountryList = new ArrayList<>();
        for (Country c : countryList) if (club.containsCountry(c)) {
            c.setCountfromclub(c.getCountFromClub(getClientClub()));
            clubCountryList.add(c);
        }
        return clubCountryList;
    }

    public Club getClientClub() {
        return clientClub;
    }

    public List<Player> getAuctionList() {
        return auctionList;
    }

    public void setClientClub (Club clientClub) {
        this.clientClub = clientClub;
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

    public Player searchByName (String name, List<Player>playerList) {
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

    public List<Player> searchByPosition (String position, List<Player>playerList) {
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

    public List<Player> searchBySalary (double r1, double r2, List<Player>playerList) {
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

    public void readFromFile () throws Exception {
        playerList.clear();
        countryList.clear();
        clubList.clear();
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
            p.setPfp();
            playerList.add(p);
        }
        br.close();

        BufferedReader br2 = new BufferedReader(new FileReader("accounts.txt"));
        while (true) {
            String line = br2.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");

            Club club = getClub(tokens[0]);
            club.setBalance(Double.parseDouble(tokens[1]));
        }

        br2.close();
    }

    public void readCredentialsFromFile() throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("credentials.txt"));

        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");

            Club club = getClub(tokens[0]);
            club.setPasswordHash(tokens[1]);
        }

        br.close();
    }

    public void resetCredentials() {
        for (Club c : clubList) c.setPasswordHash(null);
    }

    public void saveToFile() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter("players.txt"));
        for (Player s : playerList) {
            bw.write(s.getName() + "," + s.getCountry() + "," + s.getAge() + "," + s.getHeight() + "," + s.getClub()
                    + "," + s.getPosition() + "," + s.getNumber() + "," + s.getSalary());
            bw.write("\n");
        }
        bw.close();
        for (Player s : playerList) {
            Files.createDirectories(Paths.get("pfp"));
            Files.write(Paths.get("pfp/" + s.getName() + ".png"), s.getPfpBytes());
            // try {
            //     File file = new File("pfp/" + s.getName() + ".png");
            //     file.createNewFile();
            //     FileOutputStream stream = new FileOutputStream(file, false);
            //     stream.write(s.getPfpBytes());
            //     stream.close();
            // } catch (Exception e) {
            //     e.printStackTrace();
            // }
        }
        for (Club c : clubList) {
            Files.createDirectories(Paths.get("clublogo"));
            Files.write(Paths.get("clublogo/" + c.getName() + ".png"), c.getLogoBytes());
        }
        for (Country c: countryList) {
            Files.createDirectories(Paths.get("cflag"));
            Files.write(Paths.get("cflag/" + c.getName() + ".png"), c.getFlagBytes());
        }

        BufferedWriter bw2 = new BufferedWriter(new FileWriter("accounts.txt"));
        for (Club c : clubList) bw2.write( c.getName() + "," + c.getBalance() + "\n" );
        bw2.close();
    }

    public void saveCredentialsToFile() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter("credentials.txt"));
        for (Club c : clubList) bw.write( c.getName() + "," + c.getPasswordHash() + "\n" );
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
