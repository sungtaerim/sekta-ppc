package com.sekta.ppc.bot.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

public interface SendMessageService {

    void sendMessage(Long chatId, String message);

    void sendMessage(Long chatId, String message, ReplyKeyboardMarkup keyboard);

    void sendMessage(Long chatId, String message, ReplyKeyboardRemove keyboard);
}
