import java.util.*;

public abstract class Team {
    private String name;
    private List<Player> players = new ArrayList<Player>();

    public Team() {}

    public Team (String name) {
        this.name = name;
    }

    public void addPlayer (Player p) {
        this.players.add(p);
    }

    public void addPlayers (List<Player> players) {
        for (Player p : players) this.players.add(p);
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static boolean teamExists (String team, List<Team> teams) {
        for (Team t : teams) if (t.getName().equalsIgnoreCase(team)) return true;
        return false;
    }

    public List<Player> getMaxSalary() {
        List<Player> maxSalList = new ArrayList<Player>();
        double max = 0;
        for (Player p : getPlayers()) if (p.getSalary() > max) max = p.getSalary();
        for (Player p : getPlayers()) if (p.getSalary() == max) maxSalList.add(p);
        return maxSalList;
    }  

    public List<Player> getMaxAge() {
        List<Player> maxAgeList = new ArrayList<Player>();
        int max = 0;
        for (Player p : getPlayers()) if (p.getAge() > max) max = p.getAge();
        for (Player p : getPlayers()) if (p.getAge() == max) maxAgeList.add(p);
        return maxAgeList;
    }   

    public List<Player> getMaxHeight() {
        List<Player> maxHeightList = new ArrayList<Player>();
        double max = 0;
        for (Player p : getPlayers()) if (p.getHeight() > max) max = p.getHeight();
        for (Player p : getPlayers()) if (p.getHeight() == max) maxHeightList.add(p);
        return maxHeightList;
    }   

    public double getClubAnnualSalary() {
        double sum = 0;
        // int count = 0;
        for (Player p : getPlayers()) sum += p.getSalary();
        // if (count == 0) return -5;
        return (sum*52);
    }   
}
