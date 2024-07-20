package com.example.admin.controllers;

import com.example.admin.services.ExcelUtilities;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;

import javax.mail.MessagingException;

import org.json.JSONObject;

import com.example.admin.models.*;

public class RegisteredController {
    JSONObject obj;

    public JSONObject registerUser(Registered registered) throws ClassNotFoundException {
        ExcelUtilities excelUtilities = new ExcelUtilities();
        JSONObject response = new JSONObject();
        response.put("command", "register");
        try {
            excelUtilities.writeToExcel(registered);
            response.put("status", true);
            response.put("reason", "Participant created successfully awaiting representative approval");
            // TODO: Inclide sending email to School rep
        } catch (Exception e) {
            response.put("status", false);
            response.put("reason", e.getMessage());
        }
        return response;
    }

    public JSONObject run() throws IOException, SQLException, ClassNotFoundException, MessagingException {
        switch (this.obj.get("command").toString()) {
            // case "login":
            // return this.login(this.obj);
            case "register":
                Registered registered = new Registered("user", "first", "last", 2, "profile", "email",
                        java.util.Date.from(Instant.now()), "password");
                return this.registerUser(registered);
            // case "viewChallenges":
            // return this.viewChallenges(this.obj);
            // case "attemptChallenge":
            // return this.attemptChallenge(this.obj);
            // case "confirm":
            // return this.confirm(this.obj);
            // case "viewApplicants":
            // return this.viewApplicants(this.obj);
            // case "attempt":
            // return this.attempt(this.obj);
            default:
                JSONObject outputObj = new JSONObject();
                outputObj.put("command", "exception");
                outputObj.put("reason", "Invalid command");
                return outputObj;
        }
    }

}
