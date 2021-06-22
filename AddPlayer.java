import java.util.*;

public abstract class AddPlayer {
    public static boolean isNameValid (String name, List<Player> playerList) {
        for (Player p : playerList) if (p.getName().equalsIgnoreCase(name)) return false;
        return true;
    }

    public static boolean isClubValid (Club club) {
        // int clubCount = 0;
        // for (Player p : playerList) if (p.getClub() == club) clubCount++;
        if (club == null) return false;
        if (club.getPlayers().size() < 7) return true;
        return false;
    }

    public static boolean isNumberValid (Club club, int number) {
        for (Player p : club.getPlayers()) if ( p.getClub()==club && p.getNumber()==number ) return false;
        return true;
    }
    
}
