package com.sekta.ppc.dao;

import com.sekta.ppc.dao.entity.Student;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentService {

	Student selectById(Long id);

	List<Student> selectAllByUser(Long userId);

	void updateLastTime(Long id);

	void updateLastMessage(Long id, LocalDateTime lastMessage);

	void insert(Student student);

	void deleteByName(String studentName);

	void deleteAllByUserId(Long userId);

	boolean existByName(String studentName);
}
