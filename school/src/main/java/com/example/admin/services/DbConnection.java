package com.example.admin.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;

public class DbConnection {
   String url = "jdbc:mysql://localhost:3306/mathematicschallenge";
   String username = "root";
   String password = "";
   Connection connection;
   Statement statement;

   public DbConnection() throws SQLException, ClassNotFoundException {
      Class.forName("com.mysql.cj.jdbc.Driver");
      this.connection = DriverManager.getConnection(this.url, this.username, this.password);
      this.statement = this.connection.createStatement();
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
      if (this.statement != null) {
         this.statement.close();
      }

      if (this.connection != null) {
         this.connection.close();
      }

   }

   public void createParticipant(String username, String firstname, String lastname, String emailAddress, String dob, String regNo, String imagePath) throws SQLException {
      String sql = "INSERT INTO participant (username, firstname, lastname, emailAddress, dob, regNo, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement stmt = this.connection.prepareStatement(sql);

      try {
         stmt.setString(1, username);
         stmt.setString(2, firstname);
         stmt.setString(3, lastname);
         stmt.setString(4, emailAddress);
         stmt.setString(5, dob);
         stmt.setString(6, regNo);
         stmt.setString(7, imagePath);
         stmt.executeUpdate();
      } catch (Throwable var13) {
         if (stmt != null) {
            try {
               stmt.close();
            } catch (Throwable var12) {
               var13.addSuppressed(var12);
            }
         }

         throw var13;
      }

      if (stmt != null) {
         stmt.close();
      }

   }

   public void createParticipantRejected(String username, String firstname, String lastname, String emailAddress, String dob, String regNo, String imagePath) throws SQLException {
      String sql = "INSERT INTO rejectedparticipant (username, firstname, lastname, emailAddress, dob, regNo, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement stmt = this.connection.prepareStatement(sql);

      try {
         stmt.setString(1, username);
         stmt.setString(2, firstname);
         stmt.setString(3, lastname);
         stmt.setString(4, emailAddress);
         stmt.setString(5, dob);
         stmt.setString(6, regNo);
         stmt.setString(7, imagePath);
         stmt.executeUpdate();
      } catch (Throwable var13) {
         if (stmt != null) {
            try {
               stmt.close();
            } catch (Throwable var12) {
               var13.addSuppressed(var12);
            }
         }

         throw var13;
      }

      if (stmt != null) {
         stmt.close();
      }

   }

   public ResultSet getChallenges() throws SQLException {
      String sql = "SELECT * FROM mathematicschallenge.challenge WHERE starting_date <= CURRENT_DATE AND closing_date >= CURRENT_DATE;";
      return this.statement.executeQuery(sql);
   }

   public ResultSet getChallengeQuestions(int challenge_id) throws SQLException {
      String sql = "SELECT qar.* FROM mathematicschallenge.question_answer_record qar JOIN mathematicschallenge.challenge_question_answer_record cqar ON qar.question_id = cqar.question_id WHERE cqar.challenge_id = ?";
      PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
      preparedStatement.setInt(1, challenge_id);
      return preparedStatement.executeQuery();
   }

   public int getAttemptScore(JSONArray attempt) throws SQLException {
      int score = 0;

      for(int i = 0; i < attempt.length(); ++i) {
         JSONObject obj = attempt.getJSONObject(i);
         if (obj.get("answer").equals("-")) {
            score += 0;
         } else {
            int var10000 = obj.getInt("question_id");
            String sql = "SELECT score FROM question_answer_record WHERE question_id = " + var10000 + " AND answer = " + String.valueOf(obj.get("answer")) + ";";
            ResultSet questionScore = this.statement.executeQuery(sql);
            if (questionScore.next()) {
               score += questionScore.getInt("score");
            } else {
               score -= 3;
            }
         }
      }

      return score;
   }

   public void createChallengeAttempt(JSONObject obj) throws SQLException {
      String sql = "INSERT INTO participant_challenge_attempt (participant_id, challenge_id, score, total) VALUES (?, ?, ?, ?)";
      PreparedStatement ps = this.connection.prepareStatement(sql);

      try {
         ps.setInt(1, obj.getInt("participant_id"));
         ps.setInt(2, obj.getInt("challenge_id"));
         ps.setInt(3, obj.getInt("score"));
         ps.setInt(4, obj.getInt("total_score"));
         ps.executeUpdate();
      } catch (Throwable var7) {
         if (ps != null) {
            try {
               ps.close();
            } catch (Throwable var6) {
               var7.addSuppressed(var6);
            }
         }

         throw var7;
      }

      if (ps != null) {
         ps.close();
      }

   }

   public ResultSet getRepresentative(String regNo) throws SQLException {
      String sqlCommand = "SELECT * FROM school WHERE regNo = " + regNo + ";";
      return this.statement.executeQuery(sqlCommand);
   }
}
