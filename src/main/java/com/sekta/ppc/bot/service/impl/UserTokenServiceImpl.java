package com.sekta.ppc.bot.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sekta.ppc.bot.service.UserTokenService;
import com.sekta.ppc.dao.entity.UserToken;
import com.sekta.ppc.utils.http.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {

    private final UserToken userToken = UserToken.getInstance();
    private final ClientRequest clientRequest;

    @Override
    public void addNewEmail(String email, String password) {
        if (!checkEmail(email)) {
            try {
                String token = clientRequest.getToken(email, password);
                userToken.add(email, token);
            } catch (IOException exception) {
                log.error("Failed to get token: email {}, token {}", email, password);
            }
        }
    }

    @Override
    public boolean checkEmail(String email) {
        log.info("checkEmail(email = {})", email);
        System.out.println("userToken.isPresent(email) = " + userToken.isPresent(email));
        return userToken.isPresent(email);
    }
}
