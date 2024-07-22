package com.example.admin.models;

import java.util.Date;

public class Participants {
    private String userName;
    private String firstName;
    private String lastName;
    private Integer schoolId;
    private String profilePicture;
    private String emailAddress;
    private Date dateOfBirth;
    private String password;

    public Participants(String userName, String firstName, String lastName, Integer schoolId, String profilePicture,
            String emailAddress, Date dateOfBirth, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.schoolId = schoolId;
        this.profilePicture = profilePicture;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "Participants(userName=" + this.getUserName() + ", firstName=" + this.getFirstName() + ", lastName="
                + this.getLastName() + ", schoolId=" + this.getSchoolId() + ", profilePicture="
                + this.getProfilePicture() + ", emailAddress=" + this.getEmailAddress() + ", dateOfBirth="
                + this.getDateOfBirth() + ", password=" + this.getPassword() + ")";
    }
}