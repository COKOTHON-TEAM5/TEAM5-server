package com.team5.backend.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyDiaryEntry {

    private Long id;

    private LocalDate date;

    private int sleepTime;

    private String emotion;

    private String title;

    private String content;

}
