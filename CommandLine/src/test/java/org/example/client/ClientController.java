
package org.example.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClientController {
    User user;

    public ClientController(User user) {
        this.user = user;
    }

    private User login(JSONObject response) throws JSONException {
        if (response.getBoolean("status")) {
            this.user.id = response.getInt("participant_id");
            this.user.username = response.getString("username");
            this.user.email = response.getString("email");
            this.user.registration_number = response.getString("registration_number");
            this.user.schoolName = response.getString("schoolName");
            this.user.isStudent = response.getBoolean("isStudent");
            this.user.isAuthenticated = response.getBoolean("isAuthenticated");
            System.out.println("the user has been authenticated successfully");
            this.user.output = "[+] Successfully logged in as a " + this.user.username + (this.user.isStudent ? "(student)" : "(representative)");
        } else {
            this.user.output = "[-] " + response.get("reason").toString();
        }

        return this.user;
    }

    private User logout(JSONObject response) throws JSONException {
        if (response.getBoolean("status")) {
            this.user.isAuthenticated = false;
            this.user.output = "[+] Successfully logged out";
        } else {
            this.user.output = "[-] Logout failed: " + response.get("reason").toString();
        }
        return this.user;
    }

    private User changePassword(JSONObject response) throws JSONException {
        if (response.getBoolean("status")) {
            this.user.output = "[+] Password changed successfully";
        } else {
            this.user.output = "[-] Password change failed: " + response.get("reason").toString();
        }
        return this.user;
    }

    private User register(JSONObject response) throws JSONException{
        if (response.getBoolean("status")) {
            this.user.output = "[+] " + response.get("reason").toString();
        } else {
            this.user.output = "[-] " + response.get("reason").toString();
        }

        return this.user;
    }

    private User attemptChallenge(JSONObject response) throws JSONException {
        JSONArray questions = response.getJSONArray("questions");
        if (questions.length() == 0) {
            this.user.output = "[-] No available questions in this challenge right now";
            return this.user;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\nQUESTIONS \n\n");

            for(int i = 0; i < questions.length(); ++i) {
                JSONObject question = new JSONObject(((JSONObject)questions.get(i)).toString(4));
                String var10001 = String.valueOf(question.get("id"));
                stringBuilder.append(var10001 + ". " + question.getString("question") + "\n\n");
            }

            this.user.output = response.toString();
            return this.user;
        }
    }

    private User viewChallenges(JSONObject response) throws JSONException{
        JSONArray challenges = new JSONArray(response.getString("challenges"));
        if (challenges.length() == 0) {
            this.user.output = "[-] No open challenges are available right now";
            return this.user;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\nCHALLENGES \n\n");

            for(int i = 0; i < challenges.length(); ++i) {
                JSONObject challenge = new JSONObject(((JSONObject)challenges.get(i)).toString(4));
                String var10001 = String.valueOf(challenge.get("id"));
                stringBuilder.append("challenge id: " + var10001 + "\nchallenge name: " + challenge.getString("name") + "\ndifficulty: " + challenge.getString("difficulty") + "\nclosing date: " + challenge.getString("closing_date") + "\t\tduration: " + challenge.getInt("time_allocation") + "\n\n\n");
            }

            stringBuilder.append("Attempt a particular challenge using the command:\n-> attemptChallenge <challenge_id>\n\n");
            this.user.output = stringBuilder.toString();
            return this.user;
        }
    }

    private User submitChallenge(JSONObject response) throws JSONException {
        if (response.getBoolean("status")) {
            this.user.output = "[+] Challenge submitted successfully";
        } else {
            this.user.output = "[-] Challenge submission failed: " + response.get("reason").toString();
        }
        return this.user;
    }

    private User viewResults(JSONObject response) throws JSONException {
        JSONArray results = response.getJSONArray("results");
        if (results.length() == 0) {
            this.user.output = "[-] No results available";
            return this.user;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\nRESULTS \n\n");

            for (int i = 0; i < results.length(); ++i) {
                JSONObject result = new JSONObject(((JSONObject) results.get(i)).toString(4));
                String var10001 = String.valueOf(result.get("id"));
                stringBuilder.append("result id: " + var10001 + "\nparticipant id: " + result.getInt("participant_id") + "\nscore: " + result.getInt("score") + "\n\n");
            }

            this.user.output = stringBuilder.toString();
            return this.user;
        }
    }


    private User confirm(JSONObject response) throws JSONException{
        if (response.getBoolean("status")) {
            this.user.output = response.getString("reason");
        } else {
            this.user.output = response.getString("reason");
        }

        return this.user;
    }

    private User viewApplicants(JSONObject response) throws JSONException{
        JSONArray participants = new JSONArray(response.getString("applicants"));
        StringBuilder stringBuilder = new StringBuilder();
        String var10001 = this.user.schoolName.strip().toUpperCase();
        stringBuilder.append(var10001 + " (registration number: " + this.user.registration_number + ")\n");
        stringBuilder.append("\n");
        stringBuilder.append("Pending applicants:\n");
        int count = 1;

        for(int i = 0; i < participants.length(); ++i) {
            JSONObject participant = new JSONObject(((JSONObject)participants.get(i)).toString(4));
            stringBuilder.append("" + count + ". " + participant.getString("username") + " " + participant.getString("emailAddress") + "\n");
            ++count;
        }

        stringBuilder.append("\n");
        stringBuilder.append("Confirm a student using the commands\n");
        stringBuilder.append(" - confirm yes <username>\n");
        stringBuilder.append(" - confirm no <username>\n");
        this.user.output = stringBuilder.toString();
        return this.user;
    }

    public User exec(String responseData) throws JSONException{
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
            case "submitChallenge":
                return this.submitChallenge(response);
            case "viewResults":
                return this.viewResults(response);
            case "changePassword":
                return this.changePassword(response);
            case "logout":
                return this.logout(response);
            default:
                throw new IllegalStateException("Invalid response");
        }
    }
}

