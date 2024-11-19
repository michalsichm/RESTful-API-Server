package utb.fai.RESTAPIServer;

import java.util.regex.Pattern;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class MyUser {

    @Id
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    public MyUser() {
    }

    public MyUser(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public static boolean isUserDataValid(String email, String phoneNumber, String name) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        String phoneRegex = "^\\+?\\d+$";
        boolean phoneNumberIsValid = Pattern.matches(phoneRegex, phoneNumber);

        if (email == null || email.isEmpty()) {
            return false;
        }

        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        boolean emailIsValid = Pattern.matches(emailRegex, email);
        return emailIsValid && phoneNumberIsValid && name != null && !name.isEmpty();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

}
