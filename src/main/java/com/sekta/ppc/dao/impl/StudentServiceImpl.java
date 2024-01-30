package com.sekta.ppc.dao.impl;

import com.sekta.ppc.dao.StudentService;
import com.sekta.ppc.dao.entity.Student;
import com.sekta.ppc.dao.mapper.StudentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {


	@Override
	public Student selectById(Long id) {
		log.info("selectById(id = {})", id);
		return mapper.selectById(id);
	}


	@Override
	public List<Student> selectAllByUser(Long userId) {
		log.info("selectAllByUser(userId = {})", userId);
		return mapper.selectAllByUser(userId);
	}


	@Override
	public void updateLastTime(Long id) {
		log.info("updateLastTime(id = {})", id);
		mapper.updateLastTime(id);
	}

	@Override
	public void updateLastMessage(Long id, LocalDateTime lastMessage) {
		log.info("updateLastMessage(id = {})", id);
		mapper.updateLastMessage(id, lastMessage);
	}


	@Override
	public void insert(Student student) {
		log.info("insert(student = {})", student);
		mapper.insert(student);
	}


	@Override
	public void deleteByName(String studentName) {
		log.info("deleteByName(studentName = {})", studentName);
		mapper.deleteByName(studentName);
	}


	@Override
	public void deleteAllByUserId(Long userId) {
		log.info("deleteAllByUserId(userId = {})", userId);
		mapper.deleteAllByUserId(userId);
	}


	@Override
	public boolean existByName(String studentName) {
		log.info("existByName(studentName = {})", studentName);
		return mapper.existByName(studentName);
	}


	private final StudentMapper mapper;
}
