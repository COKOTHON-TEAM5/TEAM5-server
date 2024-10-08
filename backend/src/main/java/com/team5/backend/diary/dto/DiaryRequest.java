package com.team5.backend.diary.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiaryRequest {

    @NotEmpty(message = "Emotion is required")
    private String emotion;

    private String title;

    private String content;

}
