package com.team5.backend.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryRequest {

    private LocalDate date;

    private String emotion;

    private String title;

    private String content;

}
