import java.util.*;

public abstract class SearchPlayers {

    public static Player searchByName (String name, List<Player> playerList) {
        for (Player p : playerList) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
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

    public static HashMap<String, Integer> countryCount (List<Country> countryList) {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (Country c : countryList) counts.put(c.getName(), c.getPlayers().size());
        return counts;
    }
    
}
