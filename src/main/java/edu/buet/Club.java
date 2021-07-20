package edu.buet;
import java.util.*;
import javafx.scene.image.*;

public class Club extends Team {

    private Image logo;

    public Club() {
        super();
    }

    public Club(String name) {
        super(name);
        this.logo = new Image(getClass().getResourceAsStream("clublogo/" + this.getName() + ".png"));
    }

    public boolean isFull() {
        if (this.getPlayers().size() < 7) return false;
        return true;
    }

    public boolean isNumberValid (int number) {
        for (Player p : this.getPlayers()) if ( p.getNumber()==number ) return false;
        return true;
    }

    public List<Player> searchByClubAndCountry (Country country) {
        List<Player> clubList = new ArrayList<Player>();
        for (Player p : this.getPlayers()) if (p.getCountry() == country) clubList.add(p);
        if (clubList.size() < 1) return null;
        return clubList;
    }

    public List<Player> searchByClubAndCountry() {
        List<Player> clubList = new ArrayList<Player>();
        for (Player p : this.getPlayers()) clubList.add(p);
        if (clubList.size() < 1) return null;
        return clubList;
    }

    public static List<String> nameList (List<Club> teamList) {
        List<String> list = new ArrayList<>();
        for (Club p : teamList) list.add(p.getName());
        return list;
    }

    public Image getLogo() {
        return this.logo;
    }

}