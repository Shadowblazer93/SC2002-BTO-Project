package entity.user;
import enums.UserRole;

// Change to interface ?
public class User {
    private String nric;
    private String name;
    private String password;
    private int age;
    private String maritalStatus;
    private UserRole userRole;

    public User(String nric, String name, String password, int age, String maritalStatus, UserRole userRole) {
        this.nric = nric;
        this.name = name;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.userRole = userRole;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNRIC() {
        return nric;
    }
    public String getName(){
        return name;
    }
    public String getPassword() {
        return password;
    }
    public int getAge(){
        return age;
    }
    public String getMaritalStatus(){
        return maritalStatus;
    }
    public UserRole getUserRole() {
        return userRole;
    }
}