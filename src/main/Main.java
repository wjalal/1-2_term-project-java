
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        PlayerList playerList = new PlayerList();
        List<Club> clubList = playerList.getClubList();
        List<Country> countryList = playerList.getCountryList();
        playerList.readFromFile(clubList, countryList);

        Scanner input = new Scanner(System.in);
        int navMain = 0, navSub = 0;

        System.out.println("\n–––––––––––––––––––––––––––––––––");
        System.out.println("Football Player Management System");
        System.out.println("–––––––––––––––––––––––––––––––––\n");


        while (true) {

            switch (navMain) {

                case 0: {
                    System.out.println("Main Menu (Enter option [1-4]) :");
                    System.out.println("(1) Search Players");
                    System.out.println("(2) Search Clubs");
                    System.out.println("(3) Add Player");
                    System.out.println("(4) Exit System\n");
                    try {
                        navMain = input.nextInt();
                    } catch (InputMismatchException e) {
                        navMain = -1;
                    }
                    input.nextLine();
                    System.out.println();
                    break;
                }

                case 1: {

                    switch (navSub) {
                        case 0: {
                            System.out.println("Player Searching Options (Enter option [1-6]) :");
                            System.out.println("(1) By Player Name");
                            System.out.println("(2) By Club and Country");
                            System.out.println("(3) By Position");
                            System.out.println("(4) By Salary Range");
                            System.out.println("(5) Country-wise player count");
                            System.out.println("(6) Back to Main Menu\n");
                            try {
                                navSub = input.nextInt();
                            } catch (InputMismatchException e) {
                                navSub = -1;
                            }
                            input.nextLine();
                            System.out.println();
                            break;
                        }
                        case 1: {
                            System.out.println("Enter player name to search: ");
                            String name;
                            while (true) {
                                name = input.nextLine().strip();
                                if (name.length() < 1) System.out.println("\nPlayer name cannot be empty. Please try again.\n");
                                else break;
                            }
                            Player result = playerList.searchByName(name);
                            if (result != null) {
                                System.out.println("\nPlayer found: \n");
                                result.print();
                                System.out.println();
                            } else {
                                System.out.println("\nNo palyer called " + name + " found\n");
                                // if (playerList.guessPlayer(name) != null) {
                                //     System.out.println("Do you mean any of these: ");
                                //     for (String s : playerList.guessPlayer(name)) System.out.println("*  " +s);
                                //     System.out.println();
                                // }                                
                            }
                            navSub = 0;
                            break;
                        }
                        case 2: {
                            System.out.println("Enter country name to search: ");
                            String country;
                            while (true) {
                                country = input.nextLine().strip();
                                if (country.length() < 1) System.out.println("\nCountry name cannot be empty. Please try again.\n");
                                else break;
                            }
                            System.out.println("Enter club name to search: ");
                            String club;
                            while (true) {
                                club = input.nextLine().strip();
                                if (club.length() < 1) System.out.println("\nClub name cannot be empty. Please try again.\n");
                                else break;
                            }
                            if ( playerList.getCountry(country) != null && (club.equalsIgnoreCase("ANY") || playerList.getClub(club) != null)) {
                                List<Player> result = new ArrayList<Player>();
                                if (club.equalsIgnoreCase("ANY")) result = playerList.getCountry(country).searchByClubAndCountry();
                                else result = playerList.getCountry(country).searchByClubAndCountry(playerList.getClub(club));
                                if (result != null) {
                                    System.out.println("\n" + result.size() + " player" + (result.size() > 1 ? "s" : "") + " found: \n");
                                    for (int i = 0; i < result.size(); i++) result.get(i).print(i + 1);
                                    System.out.println();
                                } else System.out.println("\nNo such player with this country and club\n");
                            } else System.out.println("\nNo such player with this country and club\n");
                            navSub = 0;
                            break;
                        }
                        case 3: {
                            System.out.println("Enter player position to search: ");
                            String position;
                            while (true) {
                                position = input.nextLine().strip();
                                if (position.length() < 1) System.out.println("\nClub name cannot be empty. Please try again.\n");
                                else break;
                            }
                            List<Player> result = playerList.searchByPosition(position);
                            if (result != null) {
                                System.out.println("\n" + result.size() + " player" + (result.size() > 1 ? "s" : "") + " found: \n");
                                for (int i = 0; i < result.size(); i++) result.get(i).print(i + 1);
                                System.out.println();
                            } else System.out.println("\nNo such player with this position\n");
                            navSub = 0;
                            break;
                        }
                        case 4: {
                            double r1, r2;
                            while (true) {
                                try {
                                    System.out.println("Enter the 2 bounds of salary to search by range (space-separated): ");
                                    r1 = input.nextDouble();
                                    r2 = input.nextDouble();
                                    if (r1 < 0 || r2 < 0) throw (new Exception("negative"));
                                    break;
                                } catch (Exception e) {
                                    System.out.println("\nInvalid input, please try again.\n");
                                    input.nextLine();
                                }
                            }
                            List<Player> result = playerList.searchBySalary(r1, r2);
                            if (result != null) {
                                System.out.println("\n" + result.size() + " player" + (result.size() > 1 ? "s" : "") + " found: \n");
                                for (int i = 0; i < result.size(); i++) result.get(i).print(i + 1);
                                System.out.println();
                            } else System.out.println("\nNo such player with  this weekly salary range\n");
                            navSub = 0;
                            break;
                        }
                        case 5: {
                            System.out.println("\nCountry-wise count of players: \n");
                            HashMap<String, Integer> counts = playerList.countryCount();
                            int maxSize = 0;
                            for (String country : counts.keySet()) if (country.length() > maxSize) maxSize = country.length();
                            for (String country : counts.keySet()) {
                                String space = "";
                                for (int i = 1; i <= maxSize - country.length() + 1; i++) space += " ";
                                System.out.println(country + space + "—  " + counts.get(country));
                            }
                            System.out.println();
                            navSub = 0;
                            break;
                        }
                        case 6: {
                            navSub = 0;
                            navMain = 0;
                            break;
                        }
                        default: {
                            System.out.println("Invalid input. Please choose one of the submenu options (1-6).\n");
                            navSub = 0;
                        }
                    }
                    break;
                }

                case 2: {
                    switch (navSub) {
                        case 0: {
                            System.out.println("Club Searching Options (Enter option [1-5]) :");
                            System.out.println("(1) Player(s) with the maximum salary of a club");
                            System.out.println("(2) Player(s) with the maximum age of a club ");
                            System.out.println("(3) Player(s) with the maximum height of a club");
                            System.out.println("(4) Total yearly salary of a club");
                            System.out.println("(5) Back to Main Menu\n");
                            try {
                                navSub = input.nextInt();
                            } catch (InputMismatchException e) {
                                navSub = -1;
                            }
                            input.nextLine();
                            System.out.println();
                            break;
                        }
                        case 1: {
                            System.out.println("Enter club name: ");
                            String club = input.nextLine().strip();
                            if (playerList.getClub(club) != null) {
                                List<Player> result = playerList.getClub(club).getMaxSalary();
                                System.out.println("\nPlayer" + (result.size() > 1 ? "s" : "") + " with the maximum salary in " + club + ":\n");
                                for (int i = 0; i < result.size(); i++) result.get(i).print(i + 1);
                                System.out.println();
                            } else System.out.println("No such club with this name\n");
                            navSub = 0;
                            break;
                        }
                        case 2: {
                            System.out.println("Enter club name: ");
                            String club = input.nextLine().strip();
                            if (playerList.getClub(club) != null) {
                                List<Player> result = playerList.getClub(club).getMaxAge();
                                System.out.println("\nPlayer" + (result.size() > 1 ? "s" : "") + " with the maximum age in " + club + ":\n");
                                for (int i = 0; i < result.size(); i++) result.get(i).print(i + 1);
                                System.out.println();
                            } else System.out.println("No such club with this name\n");
                            navSub = 0;
                            break;
                        }
                        case 3: {
                            System.out.println("Enter club name: ");
                            String club = input.nextLine().strip();
                            if (playerList.getClub(club) != null) {
                                List<Player> result = playerList.getClub(club).getMaxHeight();
                                System.out.println("\nPlayer" + (result.size() > 1 ? "s" : "") + " with the maximum height in " + club + ":\n");
                                for (int i = 0; i < result.size(); i++) result.get(i).print(i + 1);
                                System.out.println();
                            } else System.out.println("No such club with this name\n");
                            navSub = 0;
                            break;
                        }
                        case 4: {
                            System.out.println("Enter club name: ");
                            String club = input.nextLine().strip();
                            if (playerList.getClub(club) != null) {
                                double result = playerList.getClub(club).getAnnualSalary();
                                System.out.println("\nTotal yearly salary of " + club + " is: " + Player.showSalary(result) + "\n");
                            } else System.out.println("No such club with this name\n");
                            navSub = 0;
                            break;
                        }
                        case 5: {
                            navSub = 0;
                            navMain = 0;
                            break;
                        }
                        default: {
                            System.out.println("Invalid input. Please choose one of the submenu options (1-5).\n");
                            navSub = 0;
                        }
                    }
                    break;
                }

                case 3: {

                    System.out.println("Enter new player information: \n");

                    String clubName; Club club = new Club();
                    while (true) {
                        System.out.print("Club: ");
                        clubName = input.nextLine().strip();
                        if (clubName.length() < 1) System.out.println("\nClub name cannot be empty. Please try again.\n");
                        else if ( playerList.getClub(clubName) != null && playerList.getClub(clubName).isFull() ) {
                            System.out.println("\nThis club already has 7 (maximum) players.\n");
                            navMain = 0;
                            break;
                        } else {
                            club = playerList.getClub(clubName); 
                            if (club == null) club = new Club(clubName);
                            break;
                        }
                    }
                    if (navMain == 0) break;

                    String name;
                    while (true) {
                        System.out.print("Name: ");
                        name = input.nextLine().strip();
                        if (name.length() < 1)
                            System.out.println("\nPlayer name cannot be empty. Please try again.\n");
                        else if (!playerList.isNameValid(name)) {
                            System.out.println("\nA player with this name already exists.\n");
                            navMain = 0;
                            break;
                        } else break;
                    }
                    if (navMain == 0) break;

                    String countryName; Country country = new Country();
                    while (true) {
                        System.out.print("Country: ");
                        countryName = input.nextLine().strip();
                        if (countryName.length() < 1) System.out.println("\nCountry name cannot be empty. Please try again.\n");
                        else {
                            country = playerList.getCountry(countryName); 
                            if (country == null) country = new Country(countryName);
                            break;
                        }
                    }

                    int age;
                    while (true) {
                        try {
                            System.out.print("Age: ");
                            age = input.nextInt();
                            if (age < 0) throw (new Exception("negative"));
                            break;
                        } catch (Exception e) {
                            System.out.println("\nInvalid input, please try again.\n");
                            input.nextLine();
                        }
                    }

                    double height;
                    while (true) {
                        try {
                            System.out.print("Height: ");
                            height = input.nextDouble();
                            if (height < 0) throw (new Exception("negative"));
                            break;
                        } catch (Exception e) {
                            System.out.println("\nInvalid input, please try again.\n");
                            input.nextLine();
                        }
                    }

                    input.nextLine();

                    String position;
                    while (true) {
                        System.out.print("Position: ");
                        position = input.next().strip();
                        position = position.substring(0, 1).toUpperCase() + position.substring(1).toLowerCase();
                        if (!position.equalsIgnoreCase("Defender") && !position.equalsIgnoreCase("Midfielder")
                                && !position.equalsIgnoreCase("Forward") && !position.equalsIgnoreCase("Goalkeeper")) {
                            System.out.println("\nInvalid Position type. The following types are allowed:");
                            System.out.println("* Forward");
                            System.out.println("* Midfielder");
                            System.out.println("* Defender");
                            System.out.println("* Goalkeeper");
                            System.out.println("Please try again.\n");
                        } else break;
                    }

                    int number;
                    while (true) {
                        try {
                            System.out.print("Number: ");
                            number = input.nextInt();
                            if (number < 1) throw (new Exception("non-positive"));
                            if (!club.isNumberValid(number)) throw (new Exception("pre-exists"));
                            break;
                        } catch (Exception e) {
                            if (e.getMessage() != null && e.getMessage().equals("pre-exists"))
                                System.out.println("\nPlayer with this number already exists in the club, please try a different number.\n");
                            else System.out.println("\nInvalid input, please try again.\n");
                            input.nextLine();
                        }
                    }

                    double salary;
                    while (true) {
                        try {
                            System.out.print("Weekly salary: ");
                            salary = input.nextDouble();
                            if (salary < 0) throw (new Exception("negative"));
                            break;
                        } catch (Exception e) {
                            System.out.println("\nInvalid input, please try again.\n");
                            input.nextLine();
                        }
                    }

                    input.nextLine();

                    System.out.println();
                    Player newP = new Player(name, country, age, height, club, position, number, salary);
                    System.out.println("Are you sure you want to add this palyer?\n");
                    newP.print();
                    while (true) { 
                        System.out.print("Enter [Y/N]:  ");
                        String yn = input.nextLine();
                        if (yn.equalsIgnoreCase("yes") || yn.equalsIgnoreCase("y")) {
                            playerList.add(newP);
                            System.out.println("\nPlayer added!");
                            break;
                        } else if (yn.equalsIgnoreCase("no") || yn.equalsIgnoreCase("n")) break;
                    }
                    System.out.println("\n");
                    navMain = 0;
                    break;
                }

                case 4: {
                    System.out.println("Saving data...");
                    playerList.saveToFile();
                    System.out.println("Data saved.");
                    input.close();
                    System.out.println("Exiting program...\n");
                    System.exit(0);
                }

                default: {
                    System.out.println("Invalid input. Please choose one of the Main Menu options (1-4).\n");
                    navMain = 0;
                }
            }

        }
        // saveToFile(playerList);
        // input.close();
    }
}
