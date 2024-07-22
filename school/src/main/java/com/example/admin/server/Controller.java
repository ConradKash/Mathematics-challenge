package com.example.admin.server;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.mail.MessagingException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.admin.services.*;

public class Controller {

    JSONObject obj;

    public Controller(JSONObject obj) {
        this.obj = obj;
    }

    private JSONObject login(JSONObject obj) throws SQLException, ClassNotFoundException {
        System.out.println("We are in server");
        Class.forName("com.mysql.cj.jdbc.Driver");
        DbConnection dbConnection = new DbConnection();
        JSONArray tokens = obj.getJSONArray("tokens");
        String username = tokens.get(1).toString();
        String email = tokens.get(2).toString();
        JSONObject clientResponse = new JSONObject();
        clientResponse.put("command", "login");
        clientResponse.put("username", username);
        clientResponse.put("email", email);
        String readParticipantQuery = "SELECT * FROM participants";
        ResultSet participantResultSet = dbConnection.read(readParticipantQuery);

        String regNo;
        do {
            if (!participantResultSet.next()) {
                regNo = "SELECT * FROM school_representatives";
                ResultSet representativeResultSet = dbConnection.read(regNo);

                do {
                    if (!representativeResultSet.next()) {
                        clientResponse.put("isStudent", false);
                        clientResponse.put("isAuthenticated", false);
                        clientResponse.put("status", false);
                        clientResponse.put("reason", "Invalid credentials. check the details provided");
                        return clientResponse;
                    }
                } while (!username.equals(representativeResultSet.getString("representativeName"))
                        || !email.equals(representativeResultSet.getString("representativeEmail")));

                String schoolName = representativeResultSet.getString("name");
                regNo = representativeResultSet.getString("id");
                clientResponse.put("participant_id", 0);
                clientResponse.put("schoolName", schoolName);
                clientResponse.put("regNo", regNo);
                clientResponse.put("isStudent", false);
                clientResponse.put("isAuthenticated", true);
                clientResponse.put("status", true);
                return clientResponse;
            }
        } while (!username.equals(participantResultSet.getString("username"))
                || !email.equals(participantResultSet.getString("emailAddress")));

        regNo = participantResultSet.getString("userName=");
        clientResponse.put("participant_id", participantResultSet.getInt("id"));
        clientResponse.put("regNo", participantResultSet.getInt("school_id"));
        clientResponse.put("schoolName", "undefined");
        clientResponse.put("isStudent", true);
        clientResponse.put("isAuthenticated", true);
        clientResponse.put("status", true);
        return clientResponse;
    }

    private JSONObject register(JSONObject obj)
            throws IOException, MessagingException, SQLException, ClassNotFoundException {
        System.out.println("We are here");
        JSONArray tokens = obj.getJSONArray("tokens");
        JSONObject participantObj = new JSONObject();
        participantObj.put("username", tokens.get(1));
        participantObj.put("firstname", tokens.get(2));
        participantObj.put("lastname", tokens.get(3));
        participantObj.put("emailAddress", tokens.get(4));
        participantObj.put("dob", tokens.get(5));
        participantObj.put("regNo", tokens.get(6));
        participantObj.put("imagePath", tokens.get(7));
        JSONObject clientResponse = new JSONObject();
        clientResponse.put("command", "register");
        clientResponse.put("status", true);
        clientResponse.put("reason", "Participant created successfully awaiting representative approval");
        return clientResponse;
    }

    private JSONObject attemptChallenge(JSONObject obj) throws SQLException, ClassNotFoundException {
        JSONObject clientResponse = new JSONObject();
        JSONArray questions = new JSONArray();
        DbConnection dbConnection = new DbConnection();
        int challengeId = Integer.parseInt((String) (new JSONArray(obj.get("tokens").toString())).get(1));
        ResultSet challengeQuestions = dbConnection.getChallengeQuestions(challengeId);

        while (challengeQuestions.next()) {
            JSONObject question = new JSONObject();
            question.put("id", challengeQuestions.getString("question_id"));
            question.put("question", challengeQuestions.getString("question"));
            question.put("score", challengeQuestions.getString("score"));
            questions.put(question);
        }

        clientResponse.put("command", "attemptChallenge");
        clientResponse.put("questions", questions);
        clientResponse.put("challenge_id", challengeId);
        clientResponse.put("challenge_name", challengeId);
        return clientResponse;
    }

    private JSONObject viewChallenges(JSONObject obj) throws SQLException, ClassNotFoundException {
        JSONObject clientResponse = new JSONObject();
        DbConnection dbConnection = new DbConnection();
        ResultSet availableChallenges = dbConnection.getChallenges();
        JSONArray challenges = new JSONArray();

        while (availableChallenges.next()) {
            JSONObject challenge = new JSONObject();
            challenge.put("id", availableChallenges.getInt("challenge_id"));
            challenge.put("name", availableChallenges.getString("challenge_name"));
            challenge.put("difficulty", availableChallenges.getString("difficulty"));
            challenge.put("time_allocation", availableChallenges.getInt("time_allocation"));
            challenge.put("starting_date", availableChallenges.getDate("starting_date"));
            challenge.put("closing_date", availableChallenges.getDate("closing_date"));
            challenges.put(challenge);
        }

        clientResponse.put("command", "viewChallenges");
        clientResponse.put("challenges", challenges.toString());
        return clientResponse;
    }

    private JSONObject confirm(JSONObject obj) throws IOException, SQLException, ClassNotFoundException {
        LocalStorage localStorage = new LocalStorage("participants.json");
        String username = obj.getString("username");
        JSONObject participant = localStorage.readEntryByUserName(username);
        JSONObject clientResponse = new JSONObject();
        clientResponse.put("command", "confirm");
        if (participant.length() == 0) {
            clientResponse.put("status", false);
            clientResponse.put("reason", "Invalid command check the username provided");
            return clientResponse;
        } else {
            DbConnection dbConnection = new DbConnection();
            String var10002;
            if (obj.getBoolean("confirm")) {
                dbConnection.createParticipant(participant.getString("username"), participant.getString("firstname"),
                        participant.getString("lastname"), participant.getString("emailAddress"),
                        participant.getString("dob"), participant.getString("regNo"),
                        participant.getString("imagePath"));
                localStorage.deleteEntryByUserName(username);
                var10002 = participant.getString("firstname");
                clientResponse.put("reason", var10002 + " " + participant.getString("firstname") + " "
                        + participant.getString("emailAddress") + " confirmed successfully");
            } else {
                dbConnection.createParticipantRejected(participant.getString("username"),
                        participant.getString("firstname"), participant.getString("lastname"),
                        participant.getString("emailAddress"), participant.getString("dob"),
                        participant.getString("regNo"), participant.getString("imagePath"));
                localStorage.deleteEntryByUserName(username);
                var10002 = participant.getString("firstname");
                clientResponse.put("reason", var10002 + " " + participant.getString("firstname") + " "
                        + participant.getString("emailAddress") + " rejected successfully");
            }

            clientResponse.put("status", true);
            return clientResponse;
        }
    }

    private JSONObject viewApplicants(JSONObject obj) throws IOException {
        String regNo = obj.getString("regNo");
        LocalStorage localStorage = new LocalStorage("participants.json");
        String participants = localStorage.filterParticipantsByRegNo(regNo);
        JSONObject clientResponse = new JSONObject();
        clientResponse.put("command", "viewApplicants");
        clientResponse.put("applicants", participants);
        return clientResponse;
    }

    public JSONObject attempt(JSONObject obj) throws SQLException, ClassNotFoundException {
        JSONArray attempt = obj.getJSONArray("attempt");
        DbConnection dbConnection = new DbConnection();
        JSONObject attemptEvaluation = new JSONObject();
        attemptEvaluation.put("score", dbConnection.getAttemptScore(attempt));
        attemptEvaluation.put("participant_id", obj.getInt("participant_id"));
        attemptEvaluation.put("challenge_id", obj.getInt("challenge_id"));
        attemptEvaluation.put("total_score", obj.getInt("total_score"));
        dbConnection.createChallengeAttempt(attemptEvaluation);
        return new JSONObject();
    }

    public JSONObject run() throws IOException, SQLException, ClassNotFoundException, MessagingException {
        switch (this.obj.get("command").toString()) {
            case "login":
                return this.login(this.obj);
            case "register":
                return this.register(this.obj);
            case "viewChallenges":
                return this.viewChallenges(this.obj);
            case "attemptChallenge":
                return this.attemptChallenge(this.obj);
            case "confirm":
                return this.confirm(this.obj);
            case "viewApplicants":
                return this.viewApplicants(this.obj);
            case "attempt":
                return this.attempt(this.obj);
            default:
                JSONObject outputObj = new JSONObject();
                outputObj.put("command", "exception");
                outputObj.put("reason", "Invalid command");
                return outputObj;
        }
    }
}