package member;

public class User extends Customer{

    // construtor User
    public User(String name, int phone) {
        super(name, phone);
    }
    public User(String name, int phone, String address) {
        super(name, phone, address);
    }
}