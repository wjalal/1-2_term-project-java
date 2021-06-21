import java.util.*;

public abstract class AddPlayer {
    public static boolean isNameValid (String name, List<Player> playerList) {
        for (Player p : playerList) if (p.getName().equalsIgnoreCase(name)) return false;
        return true;
    }

    public static boolean isClubValid (String club, List<Player> playerList) {
        int clubCount = 0;
        for (Player p : playerList) if (p.getClub().equalsIgnoreCase(club)) clubCount++;
        if (clubCount < 7) return true;
        return false;
    }

    public static boolean isNumberValid (String club, int number, List<Player> playerList) {
        for (Player p : playerList) if ( p.getClub().equalsIgnoreCase(club) && p.getNumber()==number ) return false;
        return true;
    }
    
}
