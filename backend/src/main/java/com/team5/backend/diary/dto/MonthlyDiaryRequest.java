package com.team5.backend.diary.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyDiaryRequest {

    @NotEmpty(message = "year is required")
    private int year;

    @NotEmpty(message = "month is required")
    private int month;

}
