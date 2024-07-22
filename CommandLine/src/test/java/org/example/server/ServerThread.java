package org.example.server;

import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.MessagingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.sql.SQLException;
import java.util.regex.Pattern;


public class ServerThread {
    private Socket socket;
    // server thread properties

    public ServerThread(Socket socket) {
        this.socket = socket;
        // define constructor for the Server thread
    }

    public JSONObject readUserInput(BufferedReader input) throws IOException, JSONException {
        String clientInput;
        StringBuilder clientIn = new StringBuilder();

        String regex = "^\\{.*\\}$";
        Pattern pattern = Pattern.compile(regex);

        // iterate until the end of the json data
        while ((clientInput = input.readLine()) != null) {
            if (pattern.matcher(clientInput).matches()) {
                clientIn.append(clientInput);
                break;
            }
            clientIn.append(clientInput);

            if (clientInput.equals("}")) {
                break;
            }
        }

        // load data into a json format
        JSONObject jsonObject = new JSONObject(clientIn.toString().strip());
        return jsonObject;
    }

    public void start() throws IOException, MessagingException,JSONException {

        System.out.println("Thread started");

        try (
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true)
        ){

            // read user input
            JSONObject clientRequest;
            while ((clientRequest = this.readUserInput(input)) != null) {
                System.out.println(socket.getInetAddress().getHostAddress() + " - - " + clientRequest.toString());

                Controller exec = new Controller(clientRequest);

                String response = exec.run().toString();

                // send content back to client
                output.println(response);
            }

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            socket.close();
        }

        // start a thread for communicating with the client continously
    }
}