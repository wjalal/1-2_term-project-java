import java.util.*;

public class SearchPlayers {

    public static Player searchByName (String name, List<Player> playerList) {
        for (Player p : playerList) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    public static List<Player> searchByClubAndCountry (String country, String club, List<Player> playerList) {
        List<Player> countryList = new ArrayList<Player>();
        for (Player p : playerList) {
            if (p.getCountry().equalsIgnoreCase(country)) {
                if ( club.equalsIgnoreCase("ANY") || p.getClub().equalsIgnoreCase(club) ) countryList.add(p);
            }
        }
        if (countryList.size() == 0) return null;
        return countryList;
    }

    public static List<Player> searchByPosition (String position, List<Player> playerList) {
        List<Player> posList = new ArrayList<Player>();
        for (Player p : playerList) {
            if (p.getPosition().equalsIgnoreCase(position)) posList.add(p);
        }
        if (posList.size() == 0) return null;
        return posList;
    }

    public static List<Player> searchBySalary (double r1, double r2, List<Player> playerList) {
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

    public static HashMap<String, Integer> countryCount (List<Player> playerList) {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (Player p : playerList) {
            if (counts.containsKey(p.getCountry())) counts.put(p.getCountry(), counts.get(p.getCountry()) + 1);
            else counts.put(p.getCountry(), 1);
        }
        return counts;
    }
    
}
