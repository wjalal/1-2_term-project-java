import java.util.*;

public abstract class SearchClubs {

    public static List<Player> getMaxSalary (String club, List<Player> playerList) {

        List<Player> clubList = new ArrayList<Player>();
        for (Player p : playerList) if (p.getClub().equalsIgnoreCase(club)) clubList.add(p);
        if (clubList.size() == 0) return null;

        List<Player> maxSalList = new ArrayList<Player>();
        double max = 0;
        for (Player p : clubList) if (p.getSalary() > max) max = p.getSalary();
        for (Player p : clubList) if (p.getSalary() == max) maxSalList.add(p);
        return maxSalList;
    }   

    public static List<Player> getMaxAge (String club, List<Player> playerList) {

        List<Player> clubList = new ArrayList<Player>();
        for (Player p : playerList) if (p.getClub().equalsIgnoreCase(club)) clubList.add(p);
        if (clubList.size() == 0) return null;

        List<Player> maxAgeList = new ArrayList<Player>();
        int max = 0;
        for (Player p : clubList) if (p.getAge() > max) max = p.getAge();
        for (Player p : clubList) if (p.getAge() == max) maxAgeList.add(p);
        return maxAgeList;
    }   

    public static List<Player> getMaxHeight (String club, List<Player> playerList) {

        List<Player> clubList = new ArrayList<Player>();
        for (Player p : playerList) if (p.getClub().equalsIgnoreCase(club)) clubList.add(p);
        if (clubList.size() == 0) return null;

        List<Player> maxHeightList = new ArrayList<Player>();
        double max = 0;
        for (Player p : clubList) if (p.getHeight() > max) max = p.getHeight();
        for (Player p : clubList) if (p.getHeight() == max) maxHeightList.add(p);
        return maxHeightList;
    }   

    public static double getClubAnnualSalary (String club, List<Player> playerList) {
        double sum = 0;
        int count = 0;
        for (Player p : playerList) if (p.getClub().equalsIgnoreCase(club)) {
            count++;
            sum += p.getSalary();
        }
        if (count == 0) return -5;
        return (sum*52);
    }   

}
