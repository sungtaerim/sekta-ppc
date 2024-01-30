package com.sekta.ppc.bot.service;

public interface UserTokenService {

    void addNewEmail(String email, String token);

    boolean checkEmail(String email);
}
