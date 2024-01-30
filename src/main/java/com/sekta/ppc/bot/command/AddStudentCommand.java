package com.sekta.ppc.bot.command;

import com.sekta.ppc.bot.command.commandUtils.CommandUtil;
import com.sekta.ppc.bot.service.KeyboardService;
import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.bot.command.commandUtils.Messages;
import com.sekta.ppc.dao.ScheduleService;
import com.sekta.ppc.dao.StudentService;
import com.sekta.ppc.dao.UserService;
import com.sekta.ppc.dao.entity.enums.BotState;
import com.sekta.ppc.dao.entity.Student;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Slf4j
public class AddStudentCommand extends CommandUtil implements Command {

	private final String regex = "[(\n+).,;]";

	public AddStudentCommand(SendMessageService sendMessageService, UserService userService, ScheduleService scheduleService, StudentService studentService, KeyboardService keyboardService) {
		super(sendMessageService, userService, scheduleService, studentService, keyboardService);
	}

	@Override
	public void execute(Update update) {
		val user = userService.getUserById(update.getMessage().getChatId());
		if (user == null) {
			sendMessageService.sendMessage(update.getMessage().getChatId(), Messages.USER_NOT_FOUND.getMessage());
			return;
		}

		switch (user.getState()) {
			case NORMAL -> {
				userService.updateState(user.getId(), BotState.STUDENTS_WAIT_ADD);
				sendMessageService.sendMessage(user.getId(), Messages.ADD_STUDENT_NAMES.getMessage());
			}

			case STUDENTS_WAIT_ADD -> {
				val message = update.getMessage().getText();
				val students = message.split(regex);

				Arrays.stream(students)
					.filter(student -> !studentService.existByName(student.trim()))
					.forEach(student -> studentService.insert(Student.builder()
						.name(student.trim())
						.user(user)
						.build()));
				userService.updateState(user.getId(), BotState.NORMAL);
				sendMessageService.sendMessage(user.getId(), Messages.ADD_STUDENT_SUCCESS.getMessage());
			}

			default -> {
				userService.updateState(user.getId(), BotState.NORMAL);
				sendMessageService.sendMessage(user.getId(), Messages.ERROR_RESET.getMessage());
			}
		}
	}
}
