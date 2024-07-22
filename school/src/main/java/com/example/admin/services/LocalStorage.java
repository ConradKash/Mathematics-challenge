package com.example.admin.services;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

public class LocalStorage {
    String filePath;

    public LocalStorage(String filePath) throws IOException {
        this.filePath = filePath;
        File file = new File(this.filePath);
        if (!file.exists()) {
            try (FileWriter fhandle = new FileWriter(this.filePath)) {
                fhandle.write((new JSONArray()).toString());
                fhandle.flush();
            } catch (Exception e) {
                System.out.println("Error creating file" + e.getMessage());
            }
        }

    }

    public void add(JSONObject newEntry) {
        JSONArray jsonArray = this.read();
        jsonArray.put(newEntry);

        try {
            FileWriter file = new FileWriter(this.filePath);

            try {
                file.write(jsonArray.toString(4));
                file.flush();
            } catch (Throwable var7) {
                try {
                    file.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }

                throw var7;
            }

            file.close();
        } catch (IOException var8) {
            var8.printStackTrace();
        }

    }

    public JSONArray read() {
        JSONArray jsonArray = new JSONArray();

        try {
            FileReader reader = new FileReader(this.filePath);

            try {
                StringBuilder jsonData = new StringBuilder();

                while (true) {
                    int i;
                    if ((i = reader.read()) == -1) {
                        if (jsonData.length() > 0) {
                            jsonArray = new JSONArray(jsonData.toString());
                        }
                        break;
                    }

                    jsonData.append((char) i);
                }
            } catch (Throwable var6) {
                try {
                    reader.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            reader.close();
        } catch (IOException var7) {
            var7.printStackTrace();
        }

        return jsonArray;
    }

    public JSONObject readEntryByUserName(String username) {
        JSONArray jsonArray = this.read();

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("username").equals(username)) {
                return jsonObject;
            }
        }

        return null;
    }

    public String filterParticipantsByRegNo(String regNo) {
        JSONArray jsonArray = this.read();
        JSONArray output = new JSONArray();

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("regNo").equals(regNo)) {
                output.put(jsonObject);
            }
        }

        return output.toString();
    }

    public void deleteEntryByUserName(String username) {
        JSONArray jsonArray = this.read();
        JSONArray updatedArray = new JSONArray();

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (!jsonObject.getString("username").equals(username)) {
                updatedArray.put(jsonObject);
            }
        }

        try {
            FileWriter file = new FileWriter(this.filePath);

            try {
                file.write(updatedArray.toString(4));
                file.flush();
            } catch (Throwable var8) {
                try {
                    file.close();
                } catch (Throwable var7) {
                    var8.addSuppressed(var7);
                }

                throw var8;
            }

            file.close();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

    }
}
