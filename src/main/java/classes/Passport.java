package classes;

public class Passport {
    private int id_passport;
    private int number;
    private String seria;

    public int getId() {
        return id_passport;
    }
    public Passport() {}
    public Passport(int number, String seria) {
        this.number = number;
        this.seria = seria;
    }
    public void setId_passport(int id_passport) {
        this.id_passport = id_passport;
    }
    public int getNumber() {
        return number;
    }
    public String getSeria() {
        return seria;
    }
}
