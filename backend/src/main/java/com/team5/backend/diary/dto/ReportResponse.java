package com.team5.backend.diary.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class ReportResponse {
    private int maxcount;

    private int status;
    private int sleepTime;

    private String emotion;
    private String content;

}
