//
// Source code recreated from a .cla

package org.example.server;

import org.json.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {
    JSONObject obj;

    public Controller(JSONObject obj) {
        this.obj = obj;
    }
    private JSONObject login(JSONObject obj) throws SQLException, ClassNotFoundException,JSONException {
        // logic to log in a student this can work with isAuthenticated == false only (!isAuthenticated)
        DbConnection dbConnection = new DbConnection();

        JSONArray tokens = obj.getJSONArray("tokens");

        String username = tokens.get(1).toString();
        String email = tokens.get(2).toString();

        JSONObject clientResponse = new JSONObject();
        clientResponse.put("command", "login");
        clientResponse.put("username", username);
        clientResponse.put("email", email);

        String readParticipantQuery = "SELECT * FROM participant";
        ResultSet participantResultSet = dbConnection.read(readParticipantQuery);
        while (participantResultSet.next()) {
            if (username.equals(participantResultSet.getString("username")) && email.equals(participantResultSet.getString("emailAddress"))) {
                // there is a match here

                String regNo = participantResultSet.getString("regNo");

                clientResponse.put("participant_id", participantResultSet.getInt("participant_id"));
                clientResponse.put("regNo", regNo);
                clientResponse.put("schoolName", "undefined");
                clientResponse.put("isStudent", true);
                clientResponse.put("isAuthenticated", true);
                clientResponse.put("status", true);

                return  clientResponse;
            }
        }


        String readRepresentativeQuery = "SELECT * FROM school";
        ResultSet representativeResultSet = dbConnection.read(readRepresentativeQuery);
        while (representativeResultSet.next()) {
            if (username.equals(representativeResultSet.getString("representativeName")) && email.equals(representativeResultSet.getString("representativeEmail"))) {
                // there is a match

                String schoolName = representativeResultSet.getString("name");
                String regNo = representativeResultSet.getString("regNo");

                clientResponse.put("participant_id", 0);
                clientResponse.put("schoolName", schoolName);
                clientResponse.put("regNo", regNo);
                clientResponse.put("isStudent", false);
                clientResponse.put("isAuthenticated", true);
                clientResponse.put("status", true);

                return clientResponse;
            }
        }

        clientResponse.put("isStudent", false);
        clientResponse.put("isAuthenticated", false);
        clientResponse.put("status", false);
        clientResponse.put("reason", "Invalid credentials. check the details provided");

        return clientResponse;
    }

    private JSONObject register(JSONObject obj) throws IOException, MessagingException, SQLException, ClassNotFoundException,JSONException {
        // logic to register student this can work with isAuthenticated == false only (!isAuthenticated)
        Email emailAgent = new Email();
        DbConnection dbConnection = new DbConnection();

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

        ResultSet rs = dbConnection.getRepresentative(participantObj.getString("regNo"));
        String representativeEmail;
        if (rs.next()) {
            representativeEmail = rs.getString("representativeEmail");
        } else {
            clientResponse.put("status", false);
            clientResponse.put("reason", "school does not exist in our database");

            return clientResponse;
        }


        LocalStorage localStorage = new LocalStorage("participants.json");
        if (!localStorage.read().toString().contains(participantObj.toString())) {
            localStorage.add(participantObj);
            clientResponse.put("status", true);
            clientResponse.put("reason", "Participant created successfully awaiting representative approval");

            emailAgent.sendParticipantRegistrationRequestEmail(representativeEmail, participantObj.getString("emailAddress"), participantObj.getString("username"));

            return clientResponse;
        }

        clientResponse.put("status", false);
        clientResponse.put("reason", "Participant creation failed found an existing participant object");

        return clientResponse;
    }

    private JSONObject attemptChallenge(JSONObject obj) throws SQLException, ClassNotFoundException,JSONException {
        // logic to attempt a challenge respond with the random questions if user is eligible (student, isAuthenticated)
        JSONObject clientResponse = new JSONObject();
        JSONArray questions = new JSONArray();

        DbConnection dbConnection = new DbConnection();

        int challengeId = Integer.parseInt((String) new JSONArray(obj.get("tokens").toString()).get(1));
        ResultSet challengeQuestions;
        challengeQuestions = dbConnection.getChallengeQuestions(challengeId);


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

    private JSONObject viewChallenges(JSONObject obj) throws SQLException, ClassNotFoundException,JSONException {
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

    private JSONObject confirm(JSONObject obj) throws IOException, SQLException, ClassNotFoundException,JSONException {
        // logic to confirm registered students (representatives, isAuthenticated)
        LocalStorage localStorage = new LocalStorage("participants.json");

        String username = obj.getString("username");
        JSONObject participant = localStorage.readEntryByUserName(username);
        JSONObject clientResponse = new JSONObject();
        clientResponse.put("command", "confirm");

        if (participant.length()==0) {
            clientResponse.put("status", false);
            clientResponse.put("reason", "Invalid command check the username provided");
            return clientResponse;
        }

        DbConnection dbConnection = new DbConnection();
        if (obj.getBoolean("confirm")) {
            dbConnection.createParticipant(participant.getString("username"), participant.getString("firstname"), participant.getString("lastname"), participant.getString("emailAddress"), participant.getString("dob"), participant.getString("regNo"), participant.getString("imagePath"));
            localStorage.deleteEntryByUserName(username);
            clientResponse.put("reason", participant.getString("firstname") + " " + participant.getString("firstname") + " " + participant.getString("emailAddress") + " confirmed successfully");
        } else {
            dbConnection.createParticipantRejected(participant.getString("username"), participant.getString("firstname"), participant.getString("lastname"), participant.getString("emailAddress"), participant.getString("dob"), participant.getString("regNo"), participant.getString("imagePath"));
            localStorage.deleteEntryByUserName(username);
            clientResponse.put("reason", participant.getString("firstname") + " " + participant.getString("firstname") + " " + participant.getString("emailAddress") + " rejected successfully");
        }
        clientResponse.put("status", true);
        return clientResponse;
    }

    private JSONObject viewApplicants(JSONObject obj) throws IOException,JSONException {
        // logic to confirm registered students (representatives, isAuthenticated)
        String regNo = obj.getString("regNo");

        LocalStorage localStorage = new LocalStorage("participants.json");

        String participants = localStorage.filterParticipantsByRegNo(regNo);

        JSONObject clientResponse = new JSONObject();
        clientResponse.put("command", "viewApplicants");
        clientResponse.put("applicants", participants);


        return clientResponse;
    }

    public JSONObject attempt(JSONObject obj) throws SQLException, ClassNotFoundException, JSONException {
        JSONArray attempt = obj.getJSONArray("attempt");
        DbConnection dbConnection = new DbConnection();

        // Initialize variables
        int totalScore = 0;
        int participantId = obj.getInt("participant_id");
        int challengeId = obj.getInt("challenge_id");

        // Loop through each attempt and calculate the score
        for (int i = 0; i < attempt.length(); i++) {
            JSONObject questionAttempt = attempt.getJSONObject(i);
            int questionId = questionAttempt.getInt("question_id");
            String answer = questionAttempt.getString("answer");

            // Get the correct answer and score for the question from the database
            JSONObject questionDetails = dbConnection.getQuestionDetails(questionId);
            String correctAnswer = questionDetails.getString("correct_answer");
            int questionScore = questionDetails.getInt("score");

            // Determine score based on the answer
            if (answer.equals(correctAnswer)) {
                totalScore += questionScore; // Correct answer
            } else if (answer.equals("-")) {
                // No score for uncertain answer
            } else {
                totalScore -= 3; // Incorrect answer, 3 marks deducted
            }
        }

        // Prepare JSON object for attempt evaluation
        JSONObject attemptEvaluation = new JSONObject();
        attemptEvaluation.put("score", totalScore);
        attemptEvaluation.put("participant_id", participantId);
        attemptEvaluation.put("challenge_id", challengeId);
        attemptEvaluation.put("total_score", totalScore);

        // Save attempt record in the database
        dbConnection.createChallengeAttempt(attemptEvaluation);

        // Return the attempt evaluation JSON
        return attemptEvaluation;
    }



    public JSONObject run() throws IOException, SQLException, ClassNotFoundException, MessagingException,JSONException {
        switch (this.obj.get("command").toString()) {
            case "login":
                // call login logic
                return this.login(this.obj);

            case "register":
                // call login logic
                return this.register(this.obj);

            case "viewChallenges":
                // call login logic
                return this.viewChallenges(this.obj);

            case "attemptChallenge":
                // call login logic
                return this.attemptChallenge(this.obj);

            case "confirm":
                // call login logic
                return this.confirm(this.obj);

            case "viewApplicants":
                return this.viewApplicants(this.obj);

            case "attempt":
                // handle attempts here
                return this.attempt(this.obj);

            default:
                // command unresolved
                JSONObject outputObj = new JSONObject();
                outputObj.put("command", "exception");
                outputObj.put("reason", "Invalid command");

                return outputObj;
        }
    }
}