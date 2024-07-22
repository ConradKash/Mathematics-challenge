package com.example.admin;

public class User {
    public String username;
    public String email;
    public String regNo;
    public int id;
    public String schoolName;
    public boolean isAuthenticated = false;
    public boolean isStudent = true;
    public String output;
 
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
 