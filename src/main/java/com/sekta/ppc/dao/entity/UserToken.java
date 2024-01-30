package com.sekta.ppc.dao.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserToken {

    static final Map<String, String> userToken = new HashMap<>();
    private static UserToken instance = null;

    public static UserToken getInstance() {
        if (instance == null) {
            instance = new UserToken();
            readEmails();
        }
        return instance;
    }

    public boolean isPresent(String email) {
        userToken
                .containsKey(email);

        return userToken.containsKey(email);
    }

    public void add(String email, String token) {
        userToken.put(email, token);
        saveEmails(email, token);
    }

    public Map<String, String> get() {
        if (userToken.isEmpty()) {
            try {
                JSONObject json = (JSONObject) new JSONParser().parse(new FileReader(getClass().getClassLoader().getResource("backup.json").getFile()));
                userToken.putAll(json);
            } catch (IOException | NullPointerException | ParseException e) {
                e.printStackTrace();
            }
        }
        return userToken;
    }

    private void saveEmails(String email, String token) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(new FileReader(getClass().getClassLoader().getResource("backup.json").getFile()));
            json.put(email, token);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(getClass().getClassLoader().getResource("backup.json").getFile()), json);

        } catch (IOException | NullPointerException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static void readEmails() {
        Map<String, String> values = new HashMap<>();
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(new FileReader(UserToken.class.getClassLoader().getResource("backup.json").getFile()));
            json.forEach((key, value) -> values.put((String) key, (String) value));
        } catch (IOException | NullPointerException | ParseException e) {
            e.printStackTrace();
        }
        userToken.putAll(values);
    }
}
