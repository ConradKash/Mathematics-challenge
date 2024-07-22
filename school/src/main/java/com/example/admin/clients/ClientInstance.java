package com.example.admin.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.admin.services.Serializer;
import com.example.admin.*;

public class ClientInstance {
    String hostname;
    int port;
    byte cache;
    String clientId;

    public ClientInstance(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public static boolean isValid(String input) {
        String regex = "^\\{.*\\}$";
        Pattern pattern = Pattern.compile(regex, 32);
        return pattern.matcher(input).matches();
    }

    public JSONArray displayQuestionSet(JSONObject challengeObj) {
        PrintStream var10000 = System.out;
        int var10001 = challengeObj.getInt("challenge_id");
        var10000.println("CHALLENGE " + var10001 + " (" + String.valueOf(challengeObj.get("challenge_name")) + ")");
        Scanner scanner = new Scanner(System.in);
        JSONArray questions = challengeObj.getJSONArray("questions");
        JSONArray solutions = new JSONArray();
        this.cache = 0;
        int count = 1;

        for (int i = 0; i < questions.length(); ++i) {
            JSONObject question = questions.getJSONObject(i);
            JSONObject answer = new JSONObject();
            this.cache += (byte) question.getInt("score");
            System.out.println("" + count + ". " + String.valueOf(question.get("question")) + " ("
                    + String.valueOf(question.get("score")) + " Marks)");
            answer.put("question_id", question.getInt("id"));
            System.out.print(" - ");
            answer.put("answer", scanner.nextLine());
            solutions.put(answer);
            ++count;
            System.out.print("\n");
        }

        return solutions;
    }

    public void start() throws IOException {
        try {
            Socket socket = new Socket(this.hostname, this.port);
            User user = new User();

            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                try {
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                    try {
                        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

                        try {
                            this.clientId = socket.getInetAddress().getHostAddress();
                            Serializer serializer = new Serializer();
                            System.out.print("[Enter the command] (" + user.username + "): ");
                            ClientController clientController = new ClientController();
                            String regex = "^\\{.*\\}$";
                            Pattern pattern = Pattern.compile(regex);

                            label197: while (true) {
                                while (true) {
                                    String userInput;
                                    if ((userInput = consoleInput.readLine()) == null) {
                                        break label197;
                                    }

                                    if (userInput.equals("logout") && user.isAuthenticated) {
                                        System.out.println("Session successfully logged out");
                                        user.logout();
                                        PrintStream var10000 = System.out;
                                        String var10001 = !user.username.isBlank() ? user.username : null;
                                        var10000.print("[Enter the command] (" + var10001 + "): ");
                                    } else {
                                        String serializedCommand = serializer.serialize(userInput);
                                        if (isValid(serializedCommand)) {
                                            output.println(serializedCommand);
                                            String response = input.readLine();
                                            System.out.println(
                                                    "this is what is passed to the client " + response);
                                            user = clientController.exec(response);
                                            if (!pattern.matcher(user.output).matches()) {
                                                System.out.println("\n" + user.output + "\n");
                                            } else {
                                                JSONObject questions = new JSONObject(user.output);
                                                JSONArray answerSet = displayQuestionSet(questions);
                                                JSONObject obj = new JSONObject();
                                                obj.put("attempt", answerSet);
                                                obj.put("participant_id", user.id);
                                                obj.put("command", "attempt");
                                                obj.put("challenge_id", questions.getInt("challenge_id"));
                                                obj.put("total_score", this.cache);
                                                String inp = obj.toString();
                                                output.println(inp);
                                            }
                                        } else {
                                            System.out.println(serializedCommand);
                                        }

                                        System.out.print("[Enter the command] (" + user.username + "): ");
                                    }
                                }
                            }
                        } catch (Throwable var31) {
                            try {
                                consoleInput.close();
                            } catch (Throwable var30) {
                                var31.addSuppressed(var30);
                            }

                            throw var31;
                        }

                        consoleInput.close();
                    } catch (Throwable var32) {
                        try {
                            output.close();
                        } catch (Throwable var29) {
                            var32.addSuppressed(var29);
                        }

                        throw var32;
                    }

                    output.close();
                } catch (Throwable var33) {
                    try {
                        input.close();
                    } catch (Throwable var28) {
                        var33.addSuppressed(var28);
                    }

                    throw var33;
                }

                input.close();
            } catch (Throwable var34) {
                try {
                    socket.close();
                } catch (Throwable var27) {
                    var34.addSuppressed(var27);
                }

                throw var34;
            }

            socket.close();
        } catch (Exception var35) {
            var35.printStackTrace();
        } finally {
            System.out.println("Connection with the server timeout");
        }

    }
}
