import java.util.*;

public class Country extends Team {

    public Country() {
        super();
    }

    public Country(String name) {
        super(name);
    }

    public static Country getCountry (String country, List<Country> countryList) {
        for (Country c : countryList) if (c.getName().equalsIgnoreCase(country)) return c;
        return null;
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
}