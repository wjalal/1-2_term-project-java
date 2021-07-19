package edu.buet;

public class Player {
    private String name;
    private Country country;
    private int age;
    private double height;
    private Club club;
    private String position;
    private int number;
    private double salary;

    public Player() {}

    public Player (String name, Country country, int age, double height, Club club, String position, int number, double salary) {
        this.name = name;
        this.country = country;
        this.age = age;
        this.height = height;
        this.club = club;
        this.position = position;
        this.number = number;
        this.salary = salary;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    
    public void setCountry (Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setAge (int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }
    
    public void setHeight (double height) {
        this.height = height;
    }

    public double getHeight() {
        return this.height;
    }
    
    public void setClub(Club club) {
        this.club = club;
    }

    public Club getClub() {
        return club;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public static String showSalary(double salary) {
        StringBuffer output = new StringBuffer( String.format("%.2f", salary) );
        int commaNo = (output.length()-4)/3;
        for (int i=1; i<=commaNo; i++) output.insert( output.length() - (3*(i+1) + i - 1), ',' );
        return ("€ " + output.toString());
    }

    public void print(int n) {
        if(n>=0) System.out.println("—————————(" + n + ")—————————"); 
        System.out.println("Name     : " + name);
        System.out.println("Country  : " + country);
        System.out.println("Age      : " + age + " years");
        System.out.println("Height   : " + height + " metres");
        System.out.println("Club     : " + club);
        System.out.println("Position : " + position);
        System.out.println("Number   : " + number);
        System.out.println("Salary   : " + showSalary(salary));
        System.out.println();
    }

    public void print() {
        print(-1);
    }

}