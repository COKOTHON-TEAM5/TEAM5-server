package com.team5.backend.diary.dto;

import lombok.Getter;

@Getter
public enum Emotion {

    jealous("질투나는"),
    happy("행복한"),
    angry("화나는"),
    mystery("신비로운"),
    sad("슬픈"),
    glommy("우울한"),
    weird("이상한"),
    comfort("편안한"),
    sweet("설레는"),
    scary("무서운")
    ;


    private final String description;

    Emotion(String description) {
        this.description = description;
    }

    public static boolean contains(String value) {
        for (Emotion emotion : Emotion.values()) {
            if (emotion.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

}
