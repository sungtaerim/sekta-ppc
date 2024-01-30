package com.sekta.ppc.bot.service;

import com.sekta.ppc.dao.entity.Student;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.List;

public interface KeyboardService {

    ReplyKeyboardMarkup getScheduleKeyboard();

    ReplyKeyboardMarkup getDaysKeyboard();

    ReplyKeyboardMarkup getYesNoKeyboard();

    ReplyKeyboardMarkup getStudentsKeyboard(List<Student> students);

    ReplyKeyboardMarkup getMinutesKeyboard();


    ReplyKeyboardRemove getRemoveKeyboard();

}
