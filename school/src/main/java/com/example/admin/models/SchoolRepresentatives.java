package com.example.admin.models;

public class SchoolRepresentatives {
    private String name;
    private String email;
    private String phone;
    private Integer schoolId;
    private String password;

    public SchoolRepresentatives(String name, String email, String phone, Integer schoolId, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.schoolId = schoolId;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
