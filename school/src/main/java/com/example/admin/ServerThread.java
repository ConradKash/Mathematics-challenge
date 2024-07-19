package com.example.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import org.json.JSONObject;

public class ServerThread {
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public JSONObject readUserInput(BufferedReader input) throws IOException {
        StringBuilder clientIn = new StringBuilder();
        String regex = "^\\{.*\\}$";
        Pattern pattern = Pattern.compile(regex);

        String clientInput;
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

        JSONObject jsonObject = new JSONObject(clientIn.toString().strip());
        return jsonObject;
    }

    public void start() throws IOException, MessagingException {
        System.out.println("Thread started");

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            try {
                PrintWriter output = new PrintWriter(this.socket.getOutputStream(), true);

                JSONObject clientRequest;
                try {
                    while ((clientRequest = this.readUserInput(input)) != null) {
                        PrintStream var10000 = System.out;
                        String var10001 = this.socket.getInetAddress().getHostAddress();
                        var10000.println(var10001 + " - - " + clientRequest.toString());
                        Controller exec = new Controller(clientRequest);
                        String response = exec.run().toString();
                        output.println(response);
                    }
                } catch (Throwable var15) {
                    try {
                        output.close();
                    } catch (Throwable var14) {
                        var15.addSuppressed(var14);
                    }

                    throw var15;
                }

                output.close();
            } catch (Throwable var16) {
                try {
                    input.close();
                } catch (Throwable var13) {
                    var16.addSuppressed(var13);
                }

                throw var16;
            }

            input.close();
        } catch (SQLException | ClassNotFoundException | IOException var17) {
            var17.printStackTrace();
            throw new RuntimeException(var17);
        } finally {
            this.socket.close();
        }

    }
}
