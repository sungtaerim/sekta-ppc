package com.sekta.ppc.utils.http;

import com.sekta.ppc.dao.entity.Student;
import com.sekta.ppc.utils.http.dto.StudentPpcDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ClientRequest {

	String getToken(String login, String password) throws IOException;

	Map<String, LocalDateTime> getMessages(String token) throws IOException;

	StudentPpcDto getAllStudents(String token);

//	Map<String, LocalDateTime> getMessages(String token, List<Student> students) throws IOException;
}
