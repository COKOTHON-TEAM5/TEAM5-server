package com.team5.backend.diary.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter

public class ReportResponse {
    private long maxcount;
    private String emotion;
    private String content;
    private int status;
}
