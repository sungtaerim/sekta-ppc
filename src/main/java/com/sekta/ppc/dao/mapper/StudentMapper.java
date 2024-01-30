package com.sekta.ppc.dao.mapper;

import com.sekta.ppc.dao.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentMapper {

    Student selectById(Long id);

    List<Student> selectAllByUser(Long userId);

    void insert(Student student);

	void deleteByName(@Param("name") String studentName);

    void deleteAllByUserId(Long userId);

    void updateLastTime(Long id);

    void updateLastMessage(Long id, LocalDateTime lastMessage);

	boolean existByName(String studentName);
}
