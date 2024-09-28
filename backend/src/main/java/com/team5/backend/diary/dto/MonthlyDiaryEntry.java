package com.team5.backend.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyDiaryEntry {

    private Long id;

    private LocalDate date;

    private LocalDateTime sleepTime;
    private LocalDateTime wakeupTime;

    private String emotion;

    private String title;

    private String content;

}
