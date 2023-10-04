package member;

public class Admin extends Customer{
    private String id = "Admin";
    private String password = "Admin1234";
    public Admin(String name, int phone) {
        super(name, phone);
    }
    // get admin id, password
    public String getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
}