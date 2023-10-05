package database;

public class SQLQueries {
    public static final String INSERT_PASSPORT = "INSERT INTO passport (number_passport, series_passport) VALUES (?, ?)";
    public static final String INSERT_ACCOUNT = "INSERT INTO account_ (PIB, id_passport, phone, id_number_specialty, reason, id_last_job, " +
            "id_specialist, data_on) VALUES (?, ?, ?, ?, ?, ?, ?, current_date())";
    public static final String INSERT_ACCOUNT_AR = "insert into account_ar values (?, ?, ?, current_date(), ?, ?, ?)";
    public static final String INSERT_VACANCY = "INSERT INTO vacancy (id_employer, PIB_responsinle, phone_responsinle, name_position, skills, zp, id_number_specialty) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public  static final String VACANCY_DETAILS = "SELECT name_position, name_specialty, zp, skills, name_employer, PIB_responsinle, phone_responsinle " +
            "FROM vacancy INNER JOIN employer ON employer.id_employer = vacancy.id_employer " +
            "INNER JOIN specialty ON specialty.id_number_specialty = vacancy.id_number_specialty " +
            "INNER JOIN address ON address.id_address = employer.id_address " +
            "WHERE id_center = ?";
    public static final String ACCOUNT_DETAILS = "SELECT account_.PIB, number_passport, series_passport, account_.phone, name_specialty, data_on " +
            "FROM account_ " +
            "INNER JOIN passport ON passport.id_passport = account_.id_passport " +
            "INNER JOIN specialty ON specialty.id_number_specialty = account_.id_number_specialty " +
            "INNER JOIN specialist ON account_.id_specialist = specialist.id_specialist " +
            "WHERE id_center = ?";

    public static final String SELECT_SPECIALIST_ID = "SELECT id_specialist FROM enter WHERE login = ? and pass = ?";
    public static final String SELECT_INFO_SPECIALIST =  "SELECT * FROM specialist INNER JOIN center ON specialist.id_center = center.id_center WHERE id_specialist = ?";
        public static final String SELECT_NUMBER_AND_SERIA = "SELECT number_passport, series_passport FROM passport";
    public static final String SELECT_CITY = "SELECT city FROM address WHERE id_center = ?";
    public static final String SELECT_SPECIALTY_ID = "SELECT id_number_specialty FROM specialty WHERE name_specialty = ?";
    public static final String SELECT_EMPLOYER_ID = "SELECT id_employer FROM employer WHERE name_employer = ?";
    public static final String SELECT_EMPLOYER_NAME = "SELECT name_employer from employer " +
            "INNER JOIN address ON employer.id_address = address.id_address " +
            "WHERE id_center = ?";
    public static final String SELECT_VACANCY_ALL = "SELECT name_position, name_specialty, zp,  skills, name_employer, PIB_responsinle, phone_responsinle\n" +
            "FROM vacancy INNER JOIN employer ON employer.id_employer = vacancy.id_employer\n" +
            "INNER JOIN specialty ON specialty.id_number_specialty = vacancy.id_number_specialty\n" +
            "INNER JOIN address ON address.id_address = employer.id_address\n" +
            "WHERE name_specialty = ? and id_center = ? ";
    public static final String SELECT_ACCOUNT_ALL = "SELECT * FROM account_ WHERE id_passport = ?";
    public  static  final String SELECT_SPECIALTY_NAME = "SELECT name_specialty FROM specialty";
    public  static final String SELEC_EMPLOYER_NAME = "SELECT name_employer FROM employer";
    public static final String SELECT_PASSPORT_ID = "SELECT id_passport FROM passport WHERE number_passport = ? and series_passport = ? ";
    public static final String SELECT_SPECIALTY_ID_NUMBER = "SELECT id_number_specialty FROM specialty WHERE name_specialty = ?";
    public static final String DELETE_ACCOUNT = "DELETE FROM account_ WHERE id_passport = ? ";
    public static final String DELETE_VACANCY = "DELETE FROM vacancy WHERE name_position = ? and id_employer = ?";
}


