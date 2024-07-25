
package org.example.client;

public class User {
    String username;
    String email;
    String 	registration_number;
    int id;
    String schoolName;
    boolean isAuthenticated = false;
    boolean isStudent = true;
    String output;

    public User() {
    }

    public void logout() {
        this.username = "";
        this.email = "";
        this.registration_number = "";
        this.isAuthenticated = false;
    }

    public void login(String username, String email, String registration_number, String schoolName, boolean isStudent) {
        this.username = username;
        this.email = email;
        this.registration_number = registration_number;
        this.schoolName = schoolName;
        this.isStudent = isStudent;
        this.isAuthenticated = true;
    }
}

