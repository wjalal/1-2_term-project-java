package edu.buet;

import java.io.Serializable;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import javafx.scene.image.*;
import java.nio.file.*;

public class Player implements Serializable {
    private String name;
    private Country country;
    private int age;
    private double height;
    private Club club;
    private String position;
    private int number;
    private double salary;
    private byte[] pfpBytes;
    private boolean auctioned;
    private double price;

    public Player() {
        this.auctioned = false;
    }

    public Player (String name, Country country, int age, double height, Club club, String position, int number, double salary) throws Exception {
        this.name = name;
        this.country = country;
        this.age = age;
        this.height = height;
        this.club = club;
        this.position = position;
        this.number = number;
        this.salary = salary;
        this.setPfp();
        this.auctioned = false;
    }

    public Player (String name, Country country, int age, double height, Club club, String position, int number, double salary, byte[] pfpBytes) throws Exception {
        this.name = name;
        this.country = country;
        this.age = age;
        this.height = height;
        this.club = club;
        this.position = position;
        this.number = number;
        this.salary = salary;
        this.pfpBytes = pfpBytes;
        this.auctioned = false;
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

    public Player getPlayer() {
        return this;
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

    public void setPfpBytes(byte[] pfpBytes) {
        this.pfpBytes = pfpBytes;
    }

    public void setPfp() throws Exception {
        this.pfpBytes = Files.readAllBytes(Paths.get("pfp/" + this.getName() + ".png"));
    }

    public Image getPfp() {
        InputStream is = new ByteArrayInputStream(pfpBytes); 
        Image pfp = new Image(is);
        return pfp;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public void transferTo (Club c) {
        if (this.isAuctioned()) {
            this.getClub().setBalance (this.club.getBalance() + this.price);
            this.getClub().getPlayers().remove(this);
            this.setAuctionState(false);
            this.setClub(c);
            this.getClub().setBalance (this.club.getBalance() - this.price);
            c.getPlayers().add(this);
        }
    }

    public byte[] getPfpBytes() {
        return pfpBytes;
    }

    public boolean isAuctioned() {
        return this.auctioned;
    }

    public void setAuctionState (boolean state) {
        this.auctioned = state;
    }


    public String getSalaryLabel() {
        return Player.showSalary(salary);
    }
    
    public String getPriceLabel() {
        return Player.showSalary(price);
    }
}