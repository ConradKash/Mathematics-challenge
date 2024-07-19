package com.example.admin;

public class User {
    String username;
    String email;
    String regNo;
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
       this.regNo = "";
       this.isAuthenticated = false;
    }
 
    public void login(String username, String email, String regNo, String schoolName, boolean isStudent) {
       this.username = username;
       this.email = email;
       this.regNo = regNo;
       this.schoolName = schoolName;
       this.isStudent = isStudent;
       this.isAuthenticated = true;
    }
 }
 