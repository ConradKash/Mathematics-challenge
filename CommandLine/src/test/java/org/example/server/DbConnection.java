package org.example.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DbConnection {
    // database connection parameters
    String url = "jdbc:mysql://localhost:3306/mtc_challenge_comp";
    String username = "root";
    String password = "";
    Connection connection;
    Statement statement;

    public DbConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        this.statement = connection.createStatement();
    }

    public void create(String sqlCommand) throws SQLException {
        this.statement.execute(sqlCommand);
    }

    public ResultSet read(String sqlCommand) throws SQLException {
        return this.statement.executeQuery(sqlCommand);
    }

    public void update(String sqlCommand) throws SQLException {
        this.statement.execute(sqlCommand);
    }

    public void delete(String sqlCommand) throws SQLException {
        this.statement.execute(sqlCommand);
    }

    public void close() throws SQLException {
        if (this.statement != null) this.statement.close();
        if (this.connection != null) this.connection.close();
    }

    public void createParticipant(String username, String firstname, String lastname, String emailAddress, String dob, String registration_number, String imagePath) throws SQLException {
        String sql = "INSERT INTO participants (username, firstname, lastname, emailAddress, dob, registration_number, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, firstname);
            stmt.setString(3, lastname);
            stmt.setString(4, emailAddress);
            stmt.setString(5, dob);
            stmt.setString(6, registration_number);
            stmt.setString(7, imagePath);
            stmt.executeUpdate();
        }
    }

    public void createParticipantRejected(String username, String firstname, String lastname, String emailAddress, String dob, String registration_number, String imagePath) throws SQLException {
        String sql = "INSERT INTO rejectedparticipant (username, firstname, lastname, emailAddress, dob, registration_number, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, firstname);
            stmt.setString(3, lastname);
            stmt.setString(4, emailAddress);
            stmt.setString(5, dob);
            stmt.setString(6, registration_number);
            stmt.setString(7, imagePath);
            stmt.executeUpdate();
        }
    }

    public ResultSet getChallenges() throws SQLException {
        String sql = "SELECT * FROM mtc_challenge_comp.challenges WHERE starting_date <= CURRENT_DATE AND closing_date >= CURRENT_DATE;";
        return this.statement.executeQuery(sql);
    }

    public ResultSet getChallengeQuestions(int challenge_id) throws SQLException {
        String sql =
                "SELECT qar.*\n" +
                "FROM mtc_challenge_comp.question_answer_records qar\n" +
                "ORDER BY RAND()\n" +
                "LIMIT 10";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        //preparedStatement.setInt(1, challenge_id);
        return preparedStatement.executeQuery();
    }


    public JSONObject getQuestionDetails(int questionId) throws SQLException {
        String sql = "SELECT correct_answer, score FROM question_answer_records WHERE question_id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            JSONObject questionDetails = new JSONObject();
            if (rs.next()) {
                questionDetails.put("correct_answer", rs.getString("correct_answer"));
                questionDetails.put("score", rs.getInt("score"));
            } else {
                throw new SQLException("Question with ID " + questionId + " not found.");
            }

            return questionDetails;
        }
    }

    public void createChallengeAttempt(JSONObject obj) throws SQLException, JSONException {
        String sql = "INSERT INTO participant_challenge_attempts(participant_id, challenge_id, score, total_score) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, obj.getInt("participant_id"));
            ps.setInt(2, obj.getInt("challenge_id"));
            ps.setInt(3, obj.getInt("score"));
            ps.setInt(4, obj.getInt("total_score"));
            ps.executeUpdate();
        }
    }

    public void insertAttempt(JSONObject obj) throws SQLException, JSONException {
        String sql = "INSERT INTO attempts (participant_id, challenge_id, question_id, answer, is_correct, created_at, updated_at) VALUES (?, ?, ?, ?, ?, NOW(), NOW())";
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, obj.getInt("participant_id"));
            ps.setInt(2, obj.getInt("challenge_id"));
            ps.setInt(3, obj.getInt("question_id"));
            ps.setString(4, obj.getString("answer"));
            ps.setBoolean(5, obj.getBoolean("is_correct"));
            ps.executeUpdate();
        }
    }

    public ResultSet getRepresentative(String registration_number) throws SQLException {
        String sqlCommand = "SELECT * FROM schools WHERE registration_number = ?";
        PreparedStatement stmt = this.connection.prepareStatement(sqlCommand);
        stmt.setString(1, registration_number);
        return stmt.executeQuery();
    }

    public ResultSet getParticipantsForChallenge(int challengeId) throws SQLException {
        String sql = "SELECT p.participant_id, p.emailAddress " +
                "FROM participants p " +
                "JOIN participant_attempts pa ON p.participant_id = pa.participant_id " +
                "WHERE pa.challenge_id = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setInt(1, challengeId);
        return preparedStatement.executeQuery();
    }

    public ResultSet getParticipantAttempts(int challengeId, int participantId) throws SQLException {
        String sql = "SELECT q.question, pa.answer, q.correct_answer, q.score " +
                "FROM participant_attempts pa " +
                "JOIN questions q ON pa.question_id = q.question_id " +
                "WHERE pa.challenge_id = ? AND pa.participant_id = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setInt(1, challengeId);
        preparedStatement.setInt(2, participantId);
        return preparedStatement.executeQuery();
    }


    public void getCorrectAnswer(JSONObject obj) throws SQLException, JSONException {
        String sql = "select from score,answer from answer(participant_id, challenge_id, question_id, is_correct) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, obj.getInt("participant_id"));
            ps.setInt(2, obj.getInt("challenge_id"));
            ps.setInt(3, obj.getInt("question_id"));
            ps.setBoolean(4, obj.getBoolean("is_correct"));
            ps.executeUpdate();
        }
    }

}

