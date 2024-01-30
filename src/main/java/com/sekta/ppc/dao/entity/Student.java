package com.sekta.ppc.dao.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Long id;
    private String name;
    private User user;
    private LocalDateTime lastUpdate;
    private LocalDateTime lastMessage;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Student {\n");

        sb.append("    id: ").append(id).append("\n");
        sb.append("    name: ").append(name).append("\n");
        sb.append("    lastUpdate: ").append(lastUpdate).append("\n");
        sb.append("    lastMessage: ").append(lastMessage).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
