public class Player {
    private String name;
    private String country;
    private int age;
    private double height;
    private String club;
    private String position;
    private int number;
    private double salary;

    public Player() {}

    public Player (String name, String country, int age, double height, String club, String position, int number, double salary) {
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
    
    public void setCountry (String country) {
        this.country = country;
    }

    public String getCountry() {
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
    
    public void setClub(String club) {
        this.club = club;
    }

    public String getClub() {
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


    public void print() {
        System.out.println(name + ", " + country + ", " + age + ", " + height + ", " + club + ", " + position + ", " + number + ", " + salary);
    }

}