package org.example.server;

import org.json.*;
import java.io.*;

public class LocalStorage {
    String filePath;

    public LocalStorage(String filePath) throws IOException {
        this.filePath = filePath;
        File file = new File(this.filePath);
        if (!file.exists()) {
            FileWriter fhandle = new FileWriter(this.filePath);
            fhandle.write(new JSONArray().toString());
            fhandle.flush();
        }
    }

    public void add(JSONObject newEntry) throws JSONException {
        JSONArray jsonArray = this.read();
        jsonArray.put(newEntry);
        try (FileWriter file = new FileWriter(this.filePath)) {
            file.write(jsonArray.toString(4));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray read() throws JSONException {
        JSONArray jsonArray = new JSONArray();

        try (FileReader reader = new FileReader(this.filePath)) {
            StringBuilder jsonData = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                jsonData.append((char) i);
            }
            if (jsonData.length() > 0) {
                jsonArray = new JSONArray(jsonData.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONObject readEntryByUserName(String username) throws JSONException{
        JSONArray jsonArray = this.read();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("username").equals(username)) {
                return jsonObject;
            }
        }
        return null;
    }

    public String filterParticipantsByRegNo(String regNo) throws JSONException{
        JSONArray jsonArray = this.read();
        JSONArray output = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("regNo").equals(regNo)) {
                output.put(jsonObject);
            }
        }
        return output.toString();
    }

    public void deleteEntryByUserName(String username) throws JSONException {
        JSONArray jsonArray = this.read();
        JSONArray updatedArray = new JSONArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (!jsonObject.getString("username").equals(username)) {
                updatedArray.put(jsonObject);
            }
        }

        try (FileWriter file = new FileWriter(this.filePath)) {
            file.write(updatedArray.toString(4));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void delete() {
        File file = new File(this.filePath);
        file.delete();
    }
}