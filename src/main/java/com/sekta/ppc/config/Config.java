package com.sekta.ppc.config;


import com.sekta.ppc.bot.service.KeyboardService;
import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.bot.service.UserTokenService;
import com.sekta.ppc.bot.service.impl.KeyboardServiceImpl;
import com.sekta.ppc.bot.service.impl.UserTokenServiceImpl;
import com.sekta.ppc.dao.ScheduleService;
import com.sekta.ppc.dao.StudentService;
import com.sekta.ppc.dao.UserService;
import com.sekta.ppc.dao.impl.ScheduleServiceImpl;
import com.sekta.ppc.dao.impl.StudentServiceImpl;
import com.sekta.ppc.dao.impl.UserServiceImpl;
import com.sekta.ppc.dao.mapper.ScheduleMapper;
import com.sekta.ppc.dao.mapper.StudentMapper;
import com.sekta.ppc.dao.mapper.UserMapper;
import com.sekta.ppc.utils.http.ClientRequest;
import com.sekta.ppc.utils.http.ClientRequestImpl;
import com.sekta.ppc.utils.schedule.BotScheduleService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@MapperScan("ru.sekta.ppc.dao.mapper")
public class Config {

    @Bean
    public SqlSession session() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession(true);
    }

    @Bean
    public UserMapper userMapper() throws IOException {
        return session().getMapper(UserMapper.class);
    }

    @Bean
    public UserService userService(UserMapper mapper) {
        return new UserServiceImpl(mapper);
    }

    @Bean
    public StudentMapper studentMapper() throws IOException {
        return session().getMapper(StudentMapper.class);
    }

    @Bean
    public StudentService studentService(StudentMapper mapper) {
        return new StudentServiceImpl(mapper);
    }

    @Bean
    public ScheduleMapper scheduleMapper() throws IOException {
        return session().getMapper(ScheduleMapper.class);
    }

    @Bean
    public ScheduleService scheduleService(ScheduleMapper mapper) {
        return new ScheduleServiceImpl(mapper);
    }

    @Bean
    public UserTokenService userTokenService(ClientRequest clientRequest) {
        return new UserTokenServiceImpl(clientRequest);
    }

    @Bean
    public KeyboardService keyboardService() {
        return new KeyboardServiceImpl();
    }

    @Bean
    public ClientRequest clientRequest(
            @Value("${url.login}") String urlLogin,
            @Value("${url.students}") String urlStudents,
            @Value("${url.messages}") String urlMessages
    ) {
        return new ClientRequestImpl(urlLogin, urlMessages, urlStudents);
    }

    @Bean
    public BotScheduleService botScheduleService(
        SendMessageService sendMessageService,
        ClientRequest clientRequest,
        UserService userService,
        StudentService studentService,
        ScheduleService scheduleService
    ) {
        return new BotScheduleService(sendMessageService, clientRequest, userService, studentService, scheduleService);
    }
}