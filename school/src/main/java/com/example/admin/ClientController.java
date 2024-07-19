package com.example.admin;

import org.json.JSONArray;
import org.json.JSONObject;

public class ClientController {
    User user;

    public ClientController(User user) {
        this.user = user;
    }

    private User login(JSONObject response) {
        if (response.getBoolean("status")) {
            this.user.id = response.getInt("participant_id");
            this.user.username = response.getString("username");
            this.user.email = response.getString("email");
            this.user.regNo = response.getString("regNo");
            this.user.schoolName = response.getString("schoolName");
            this.user.isStudent = response.getBoolean("isStudent");
            this.user.isAuthenticated = response.getBoolean("isAuthenticated");
            this.user.output = "[+] Successfully logged in as a " + this.user.username
                    + (this.user.isStudent ? "(student)" : "(representative)");
        } else {
            this.user.output = "[-] " + response.get("reason").toString();
        }

        return this.user;
    }

    private User register(JSONObject response) {
        if (response.getBoolean("status")) {
            this.user.output = "[+] " + response.get("reason").toString();
        } else {
            this.user.output = "[-] " + response.get("reason").toString();
        }

        return this.user;
    }

    private User attemptChallenge(JSONObject response) {
        JSONArray questions = response.getJSONArray("questions");
        if (questions.length() == 0) {
            this.user.output = "[-] No available questions in this challenge right now";
            return this.user;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\nQUESTIONS \n\n");

            for (int i = 0; i < questions.length(); ++i) {
                JSONObject question = new JSONObject(((JSONObject) questions.get(i)).toString(4));
                String var10001 = String.valueOf(question.get("id"));
                stringBuilder.append(var10001 + ". " + question.getString("question") + "\n\n");
            }

            this.user.output = response.toString();
            return this.user;
        }
    }

    private User viewChallenges(JSONObject response) {
        JSONArray challenges = new JSONArray(response.getString("challenges"));
        if (challenges.length() == 0) {
            this.user.output = "[-] No open challenges are available right now";
            return this.user;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\nCHALLENGES \n\n");

            for (int i = 0; i < challenges.length(); ++i) {
                JSONObject challenge = new JSONObject(((JSONObject) challenges.get(i)).toString(4));
                String var10001 = String.valueOf(challenge.get("id"));
                stringBuilder.append("challenge id: " + var10001 + "\nchallenge name: " + challenge.getString("name")
                        + "\ndifficulty: " + challenge.getString("difficulty") + "\nclosing date: "
                        + challenge.getString("closing_date") + "\t\tduration: " + challenge.getInt("time_allocation")
                        + "\n\n\n");
            }

            stringBuilder.append(
                    "Attempt a particular challenge using the command:\n-> attemptChallenge <challenge_id>\n\n");
            this.user.output = stringBuilder.toString();
            return this.user;
        }
    }

    private User confirm(JSONObject response) {
        if (response.getBoolean("status")) {
            this.user.output = response.getString("reason");
        } else {
            this.user.output = response.getString("reason");
        }

        return this.user;
    }

    private User viewApplicants(JSONObject response) {
        JSONArray participants = new JSONArray(response.getString("applicants"));
        StringBuilder stringBuilder = new StringBuilder();
        String var10001 = this.user.schoolName.strip().toUpperCase();
        stringBuilder.append(var10001 + " (registration number: " + this.user.regNo + ")\n");
        stringBuilder.append("\n");
        stringBuilder.append("Pending applicants:\n");
        int count = 1;

        for (int i = 0; i < participants.length(); ++i) {
            JSONObject participant = new JSONObject(((JSONObject) participants.get(i)).toString(4));
            stringBuilder.append("" + count + ". " + participant.getString("username") + " "
                    + participant.getString("emailAddress") + "\n");
            ++count;
        }

        stringBuilder.append("\n");
        stringBuilder.append("Confirm a student using the commands\n");
        stringBuilder.append(" - confirm yes <username>\n");
        stringBuilder.append(" - confirm no <username>\n");
        this.user.output = stringBuilder.toString();
        return this.user;
    }

    public User exec(String responseData) {
        JSONObject response = new JSONObject(responseData);
        switch (response.get("command").toString()) {
            case "login":
                return this.login(response);
            case "register":
                return this.register(response);
            case "attemptChallenge":
                return this.attemptChallenge(response);
            case "viewChallenges":
                return this.viewChallenges(response);
            case "confirm":
                return this.confirm(response);
            case "viewApplicants":
                return this.viewApplicants(response);
            default:
                throw new IllegalStateException("Invalid response");
        }
    }
}
