//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.client;

import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class Serializer {
    User user;

    public Serializer(User user) {
        this.user = user;
    }

    public String login() throws JSONException {
        if (this.user.isAuthenticated) {
            return "Session already authenticated";
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter user login details (username and email)");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.println("\n");
            String[] tokens = new String[]{"login", username, email};
            JSONObject obj = new JSONObject();
            obj.put("command", "login");
            obj.put("isAuthenticated", false);
            obj.put("tokens", tokens);
            obj.put("isStudent", false);
            return obj.toString(4);
        }
    }

    public String register(String[] arr) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("command", "register");
        obj.put("isAuthenticated", this.user.isAuthenticated);
        obj.put("tokens", arr);
        obj.put("isStudent", this.user.isStudent);
        return obj.toString(4);
    }

    public String attemptChallenge(String[] tokens) throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("command", "attemptChallenge");
        obj.put("tokens", tokens);
        return obj.toString(4);
    }

    public String viewApplicants() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("command", "viewApplicants");
        obj.put("isAuthenticated", this.user.isAuthenticated);
        obj.put("isStudent", this.user.isStudent);
        obj.put("registration_number", this.user.registration_number);
        return obj.toString(4);
    }

    public String confirm(String[] arr) throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("command", "confirm");
        obj.put("username", arr[2]);
        obj.put("registration_number", this.user.registration_number);
        obj.put("confirm", arr[1].toLowerCase().equals("yes"));
        obj.put("tokens", arr);
        return obj.toString(4);
    }

    public String viewChallenges() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("command", "viewChallenges");
        obj.put("isAuthenticated", this.user.isAuthenticated);
        obj.put("isStudent", this.user.isStudent);
        return obj.toString(4);
    }

    public String logout() {
        this.user.logout();
        return "Successfully logged out";
    }

    public String serialize(String command) throws JSONException{
        String[] tokens = command.split("\\s+");
        if (!this.user.isAuthenticated && tokens[0].equals("register")) {
            return this.register(tokens);
        } else if (!this.user.isAuthenticated && tokens[0].equals("login")) {
            return this.login();
        } else if (!this.user.isAuthenticated) {
            return "Session unauthenticated first login by entering command login";
        } else if (this.user.isStudent) {
            switch (tokens[0]) {
                case "logout":
                    return this.logout();
                case "viewChallenges":
                    return this.viewChallenges();
                case "attemptChallenge":
                    return this.attemptChallenge(tokens);
                default:
                    return "Invalid student command";
            }
        } else {
            switch (tokens[0]) {
                case "logout":
                    return this.logout();
                case "confirm":
                    return this.confirm(tokens);
                case "viewApplicants":
                    return this.viewApplicants();
                default:
                    return "Invalid school representative command";
            }
        }
    }

    public static void main(String[] args) throws JSONException{
        Serializer sample = new Serializer(new User());
        sample.serialize("login frank ogenrwothjimfrank@gmail.com");
    }
}

