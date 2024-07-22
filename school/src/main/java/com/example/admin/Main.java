package com.example.admin;

import java.time.Instant;
import com.example.admin.controllers.*;
import com.example.admin.models.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        RegisteredController registeredController = new RegisteredController();
        Registered registered = new Registered("user", "first", "last", 2, "profile", "email",
                java.util.Date.from(Instant.now()), "password");
        try {
            System.out.println(registeredController.registerUser(registered));
            // ExcelUtilities excelUtilities = new ExcelUtilities();
            // excelUtilities.test();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}