package edu.buet;
import java.util.*;

public class Club extends Team {

    public Club() {
        super();
    }

    public Club(String name) {
        super(name);
    }

    public boolean isFull() {
        if (this.getPlayers().size() < 7) return false;
        return true;
    }

    public boolean isNumberValid (int number) {
        for (Player p : this.getPlayers()) if ( p.getNumber()==number ) return false;
        return true;
    }


    public static List<String> nameList (List<Club> teamList) {
        List<String> list = new ArrayList<>();
        for (Club p : teamList) list.add(p.getName());
        return list;
    }

}