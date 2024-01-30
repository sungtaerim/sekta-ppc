package com.sekta.ppc.utils.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentPpcDto {

    @JsonProperty("clientCode")
    List<StudentPpcInfoDto> data = new ArrayList<>();

    @Getter
    @Setter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public class StudentPpcInfoDto {

        @JsonProperty("id")
        private Long id;

        @JsonProperty("full_name")
        private String fullName;

        @JsonProperty("online_order")
        private OrderPpcDto onlineOrder;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public class OrderPpcDto {

        @JsonProperty("edu_progress")
        Long eduProgress;

        @JsonProperty("edu_progress_at")
        LocalDateTime eduProgressAt;

        @JsonProperty("course_name")
        String courseName;

        @JsonProperty("chat_id")
        Long chatId;

        @JsonProperty("group")
        GroupPpc group;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public class GroupPpc {

        @JsonProperty("chat_number")
        String chatNumber;

        @JsonProperty("start_date")
        LocalDateTime startDate;
    }
}
