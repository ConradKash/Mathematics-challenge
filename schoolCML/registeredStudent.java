import java.io.*;
import java.util.*;

public class registeredStudent {
    private String username;
    private String firstname;
    private String lastname;
    private String emailAddress;
    private Date dateOfBirth;
    private Integer school_registration_number;
    private String imageFile;

    public registeredStudent(String username, String firstname, String lastname, String emailAddress, Date dateOfBirth, Integer school_registration_number, String imageFile) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        this.school_registration_number = school_registration_number;
        this.imageFile = imageFile;
    }
    
    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Integer getSchool_registration_number() {
        return school_registration_number;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSchool_registration_number(Integer school_registration_number) {
        this.school_registration_number = school_registration_number;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }
}
