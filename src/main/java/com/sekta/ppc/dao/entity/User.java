package com.sekta.ppc.dao.entity;

import com.sekta.ppc.dao.entity.enums.BotState;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Data
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String ppcLogin;
    private String ppcPassword;
    private String ppcToken;
    private BotState state;
    private String name;
    private Boolean onVacation;
    private Long minutes;

    public User(Long id, String name, BotState state) {
        this.id = id;
        this.state = state;
        this.name = name;
    }

    // iluza1988@gmail.com
    // 2128506iluza

}
