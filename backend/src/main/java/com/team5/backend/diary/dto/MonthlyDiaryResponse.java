package com.team5.backend.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MonthlyDiaryResponse {

    List<MonthlyDiaryEntry> diaries;

}
