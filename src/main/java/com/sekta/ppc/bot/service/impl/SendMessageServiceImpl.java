package com.sekta.ppc.bot.service.impl;

import com.sekta.ppc.bot.Bot;
import com.sekta.ppc.bot.service.SendMessageService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
public class SendMessageServiceImpl implements SendMessageService {

    private final Bot bot;

    @Autowired
    public SendMessageServiceImpl(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        val sendMessage = SendMessage.builder()
                                                 .chatId(chatId.toString())
                                                 .text(message)
                                                 .build();
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException exception) {
            log.error("Failed send message: {}", exception.getMessage());
        }
    }

    @Override
    public void sendMessage(Long chatId, String message, ReplyKeyboardMarkup keyboard) {
        val sendMessage = SendMessage.builder()
            .chatId(chatId.toString())
            .text(message)
            .replyMarkup(keyboard)
            .build();
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException exception) {
            log.error("Failed send message: {}", exception.getMessage());
        }
    }

    @Override
    public void sendMessage(Long chatId, String message, ReplyKeyboardRemove keyboard) {
        val sendMessage = SendMessage.builder()
                .chatId(chatId.toString())
                .text(message)
                .replyMarkup(keyboard)
                .build();
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException exception) {
            log.error("Failed send message: {}", exception.getMessage());
        }
    }
}
