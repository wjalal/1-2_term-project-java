import java.util.*;

public class Club extends Team {

    public Club() {
        super();
    }

    public Club(String name) {
        super(name);
    }

    public static Club getClub (String club, List<Club> clubList) {
        for (Club c : clubList) if (c.getName().equalsIgnoreCase(club)) return c;
        return null;
    }


}