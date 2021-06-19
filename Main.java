import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class Main {

    private static final String PLAYERDATA_FILE = "players.txt";

    public static List<Player> readFromFile() throws Exception {
        List<Player> playerList = new ArrayList<Player>();
        BufferedReader br = new BufferedReader(new FileReader(PLAYERDATA_FILE));
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            String[] tokens = line.split(",");
            Player p = new Player();
            p.setName(tokens[0]);
            p.setCountry(tokens[1]);
            p.setAge(Integer.parseInt(tokens[2]));
            p.setHeight(Double.parseDouble(tokens[3]));
            p.setClub(tokens[4]);
            p.setPosition(tokens[5]);
            p.setNumber(Integer.parseInt(tokens[6]));
            p.setSalary(Double.parseDouble(tokens[7]));
            playerList.add(p);
        }
        br.close();
        return playerList;
    }

    public static void saveToFile(List<Player> playerList) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(PLAYERDATA_FILE));
        for (Player s : playerList) {
            bw.write(s.getName() + "," + s.getCountry() + "," + s.getAge() + "," + s.getHeight() + "," + s.getClub()
                    + "," + s.getPosition() + "," + s.getNumber() + "," + s.getSalary());
            bw.write("\n");
        }
        bw.close();
    }

    public static void main(String[] args) throws Exception {

        List<Player> playerList = readFromFile();
        // for (Player s : playerList) {
        // System.out.println(s.getName() + ", " + s.getCountry() + ", " + s.getAge() +
        // ", " + s.getHeight() + ", " + s.getClub() + ", " + s.getPosition() + ", " +
        // s.getNumber() + ", " + s.getSalary());
        // }
        Scanner input = new Scanner(System.in);
        int navMain = 0, navSub = 0;

        while (true) {

            switch (navMain) {

                case 0: {
                    System.out.println("Main Menu:");
                    System.out.println("(1) Search Players");
                    System.out.println("(2) Search Clubs");
                    System.out.println("(3) Add Player");
                    System.out.println("(4) Exit System\n");
                    navMain = input.nextInt();
                    input.nextLine();
                    break;
                }

                case 1: {

                    switch (navSub) {
                        case 0: {
                            System.out.println("Player Searching Options:");
                            System.out.println("(1) By Player Name");
                            System.out.println("(2) By Club and Country");
                            System.out.println("(3) By Position");
                            System.out.println("(4) By Salary Range");
                            System.out.println("(5) Country-wise player count");
                            System.out.println("(6) Back to Main Menu\n");
                            navSub = input.nextInt();
                            input.nextLine();
                            break;
                        }
                        case 1: {
                            String name = input.nextLine();
                            Player result = SearchPlayers.searchByName(name, playerList);
                            if (result != null)
                                result.print();
                            else
                                System.out.println("No palyer called " + name + " found");
                            navSub = 0;
                            break;
                        }
                        case 2: {
                            String country = input.nextLine();
                            String club = input.next();
                            List<Player> result = SearchPlayers.searchByClubAndCountry(country, club, playerList);
                            if (result != null)
                                for (Player p : result)
                                    p.print();
                            else
                                System.out.println("No such player with this country and club");
                            navSub = 0;
                            break;
                        }
                        case 3: {
                            String position = input.nextLine();
                            List<Player> result = SearchPlayers.searchByPosition(position, playerList);
                            if (result != null)
                                for (Player p : result)
                                    p.print();
                            else
                                System.out.println("No such player with this position");
                            navSub = 0;
                            break;
                        }
                        case 4: {
                            double r1 = input.nextDouble();
                            double r2 = input.nextDouble();
                            input.nextLine();
                            List<Player> result = SearchPlayers.searchBySalary(r1, r2, playerList);
                            if (result != null)
                                for (Player p : result)
                                    p.print();
                            else
                                System.out.println("No such player with  this weekly salary range");
                            navSub = 0;
                            break;
                        }
                        case 5: {
                            input.nextLine();
                            HashMap<String, Integer> counts = SearchPlayers.countryCount(playerList);
                            for (String country : counts.keySet())
                                System.out.println(country + ": " + counts.get(country));
                            navSub = 0;
                            break;
                        }
                        case 6: {
                            navSub = 0;
                            navMain = 0;
                            break;
                        }
                    }
                    break;
                }

                case 2: {
                    switch (navSub) {
                        case 0: {
                            System.out.println("Club Searching Options:");
                            System.out.println("(1) Player(s) with the maximum salary of a club");
                            System.out.println("(2) Player(s) with the maximum age of a club ");
                            System.out.println("(3) Player(s) with the maximum height of a club");
                            System.out.println("(4) Total yearly salary of a club");
                            System.out.println("(5) Back to Main Menu\n");
                            navSub = input.nextInt();
                            input.nextLine();
                            break;
                        }
                        case 1: {
                            String club = input.nextLine();
                            List<Player> result = SearchClubs.getMaxSalary(club, playerList);
                            if (result != null)
                                for (Player p : result)
                                    p.print();
                            else
                                System.out.println("No such club with this name");
                            navSub = 0;
                            break;
                        }
                        case 2: {
                            String club = input.nextLine();
                            List<Player> result = SearchClubs.getMaxAge(club, playerList);
                            if (result != null)
                                for (Player p : result)
                                    p.print();
                            else
                                System.out.println("No such club with this name");
                            navSub = 0;
                            break;
                        }
                        case 3: {
                            String club = input.nextLine();
                            List<Player> result = SearchClubs.getMaxHeight(club, playerList);
                            if (result != null)
                                for (Player p : result)
                                    p.print();
                            else
                                System.out.println("No such club with this name");
                            navSub = 0;
                            break;
                        }
                        case 4: {
                            String club = input.nextLine();
                            double result = SearchClubs.getClubAnnualSalary(club, playerList);
                            if (result > -1)
                                System.out.println(result);
                            else
                                System.out.println("No such club with this name");

                            navSub = 0;
                            break;
                        }
                        case 5: {
                            navSub = 0;
                            navMain = 0;
                            break;
                        }
                    }
                    break;
                }

                case 3: {
                    System.out.println("Enter new player information: ");
                    System.out.print("Name: ");
                    String name = input.nextLine();
                    System.out.print("Country: ");
                    String country = input.nextLine();
                    System.out.print("Age: ");
                    int age = input.nextInt();
                    System.out.print("Height: ");
                    double height = input.nextDouble();
                    input.nextLine();
                    System.out.print("Club: ");
                    String club = input.nextLine();
                    System.out.print("Position: ");
                    String position = input.next();
                    System.out.print("Number: ");
                    int number = input.nextInt();
                    System.out.print("Weekly salary: ");
                    double salary = input.nextDouble();
                    input.nextLine();
                    System.out.println();
                    playerList.add(new Player(name, country, age, height, club, position, number, salary));
                    saveToFile(playerList);
                    navMain = 0;
                    break;
                }

                case 4: {
                    input.close();
                    System.exit(0);
                }

                default: {
                    System.out.println("Invalid input");
                    navMain = 0;
                }
            }

        }
        // saveToFile(playerList);
        // input.close();
    }
}
