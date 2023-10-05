package controllers;

public class TableController {
    private  String PIBAccount;
    private int numberPassport;
    private String seriaPassport;
    private String phone;
    private String nameSpecialty;
    private String date;
    private String namePosition;
    private float salary;
    private String skills;
    private String nameEmployer;

    public TableController(String namePosition, String nameSpecialty, float salary, String skills, String nameEmployer, String PIBAccount, String phone) {
        this.PIBAccount = PIBAccount;
        this.phone = phone;
        this.nameSpecialty = nameSpecialty;
        this.namePosition = namePosition;
        this.salary = salary;
        this.skills = skills;
        this.nameEmployer = nameEmployer;
    }
    public TableController(String PIBAccount, int numberPassport, String seriaPassport, String phone, String nameSpecialty, String date) {
        this.PIBAccount = PIBAccount;
        this.numberPassport = numberPassport;
        this.seriaPassport = seriaPassport;
        this.phone = phone;
        this.nameSpecialty = nameSpecialty;
        this.date = date;
    }
    public String getNamePosition() {
        return namePosition;
    }
    public String getNameEmployer() {
        return nameEmployer;
    }
    public int getNumberPassport() {
        return numberPassport;
    }
    public String getSeriaPassport() {
        return seriaPassport;
    }
    public String getNameSpecialty() {
        return nameSpecialty;
    }
    public String getPIBAccount() {
        return PIBAccount;
    }
    public String getPhone() {
        return phone;
    }
    public String getDate() {
        return date;
    }
    public float getSalary() {
        return salary;
    }
    public String getSkills() {
        return skills;
    }
}
