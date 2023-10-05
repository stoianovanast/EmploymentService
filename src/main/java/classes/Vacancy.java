package classes;

import database.DatabaseHandler;

public class Vacancy {
    private int id_employer;
    private String PIB_employer;
    private String phone_employer;
    private String namePosition;
    private String skills;
    private float salary;
    private int id_specialty;

    public Vacancy(){}
    public void setVacancy(String namePosition, String NameSpecialty, float salary, String skills, DatabaseHandler databaseHandler){
        this.namePosition = namePosition;
        this.id_specialty = databaseHandler.getIdSpecialty(NameSpecialty);
        this.salary = salary;
        this.skills = skills;
    }
    public int getEmployer() {
        return id_employer;
    }
    public String getPIB() {
        return PIB_employer;
    }
    public String getPhoneEmployer() {
        return phone_employer;
    }
    public String getNamePosition() {
        return namePosition;
    }
    public String getSkills() {
        return skills;
    }
    public float getSalary() {
        return salary;
    }
    public int getIdSpecialty() {
        return id_specialty;
    }
    public void setEmployer(int id_employer) {
        this.id_employer = id_employer;
    }
    public void setPIBEmployer(String PIB_employer) {
        this.PIB_employer = PIB_employer;
    }
    public void setPhoneEmployer(String phone_employer) {
        this.phone_employer = phone_employer;
    }
}
