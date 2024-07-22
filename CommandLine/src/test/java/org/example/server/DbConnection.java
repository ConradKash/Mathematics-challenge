package org.example.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;

public class DbConnection {
    // database connection parameters
    String url = "jdbc:mysql://localhost:3306/mathematicschallenge";
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

    public void createParticipant(String username, String firstname, String lastname, String emailAddress, String dob, String regNo, String imagePath) throws SQLException {
        String sql = "INSERT INTO participant (username, firstname, lastname, emailAddress, dob, regNo, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, firstname);
            stmt.setString(3, lastname);
            stmt.setString(4, emailAddress);
            stmt.setString(5, dob);
            stmt.setString(6, regNo);
            stmt.setString(7, imagePath);
            stmt.executeUpdate();
        }
    }

    public void createParticipantRejected(String username, String firstname, String lastname, String emailAddress, String dob, String regNo, String imagePath) throws SQLException {
        String sql = "INSERT INTO rejectedparticipant (username, firstname, lastname, emailAddress, dob, regNo, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, firstname);
            stmt.setString(3, lastname);
            stmt.setString(4, emailAddress);
            stmt.setString(5, dob);
            stmt.setString(6, regNo);
            stmt.setString(7, imagePath);
            stmt.executeUpdate();
        }
    }

    public ResultSet getChallenges() throws SQLException {
        String sql = "SELECT * FROM mathematicschallenge.challenge WHERE starting_date <= CURRENT_DATE AND closing_date >= CURRENT_DATE;";
        return this.statement.executeQuery(sql);
    }

    public ResultSet getChallengeQuestions(int challenge_id) throws SQLException {
        String sql = "SELECT qar.*\n" +
                "FROM mathematicschallenge.question_answer_record qar\n" +
                "JOIN mathematicschallenge.challenge_question_answer_record cqar\n" +
                "    ON qar.question_id = cqar.question_id\n" +
                "WHERE cqar.challenge_id = ?\n" +
                "ORDER BY RAND()\n" +
                "LIMIT 10";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setInt(1, challenge_id);
        return preparedStatement.executeQuery();
    }

    public JSONObject getQuestionDetails(int questionId) throws SQLException {
        String sql = "SELECT correct_answer, score FROM question_answer_record WHERE question_id = ?";
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
        String sql = "INSERT INTO participant_challenge_attempt (participant_id, challenge_id, score, total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, obj.getInt("participant_id"));
            ps.setInt(2, obj.getInt("challenge_id"));
            ps.setInt(3, obj.getInt("score"));
            ps.setInt(4, obj.getInt("total_score"));
            ps.executeUpdate();
        }
    }

    public ResultSet getRepresentative(String regNo) throws SQLException {
        String sqlCommand = "SELECT * FROM school WHERE regNo = ?";
        PreparedStatement stmt = this.connection.prepareStatement(sqlCommand);
        stmt.setString(1, regNo);
        return stmt.executeQuery();
    }
}
