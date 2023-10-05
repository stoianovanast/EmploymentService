package classes;

public class EmployService {
    private int id_specialist;
    private String PIB_specialist;
    private String phone_specialist;
    private int id_center;
    private String address_center;
    private String city_center;
    public EmployService(int id_specialist, String PIB_specialist, String phone_specialist, int id_center, String address_center) {
        this.id_specialist = id_specialist;
        this.PIB_specialist = PIB_specialist;
        this.phone_specialist = phone_specialist;
        this.id_center = id_center;
        this.address_center = address_center;
    }
    public int getId_specialist() {
        return id_specialist;
    }
    public String getPIB_specialis() {
        return PIB_specialist;
    }
    public String getPhone_specialis() {
        return phone_specialist;
    }
    public int getId_center() {
        return id_center;
    }
    public String getAddress_center() {
        return address_center;
    }
    public void setCity_center(String city_center) {
        this.city_center = city_center;
    }

}
