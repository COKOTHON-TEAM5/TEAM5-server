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

    public static String getContent(String emotion) {
        switch (emotion.toLowerCase()) {
            case "jealous":
                return "꿈은 때로 감정의 해소와 조절에 중요한 역할을 한다고 해!\n 지금 느끼는 감정은 지나갈 것이고, \n 너는 충분히 소중한 사람이야~";
            case "happy":
                return "꿈은 우리의 감정 상태를 반영한다고 해!\n행복한 꿈은 너에게 더 많은 행복이 찾아올 신호일지도 몰라.\n그 기분을 오래 간직했으면 좋겠다!";
            case "angry":
                return "우리는 꿈을 통해 복잡한 감정을 경험함으로써,\n깨어 있을 때 감정을 더 잘 관리할 수 있다고 해!\n화나는 감정은 잊고 가볍게 넘기자~";
            case "mystery":
                return "동화같은 꿈을 자주 꾸는구나!\n꿈속에서 느낀 신비로움이 너의 하루에도 작은 마법처럼 \n번지길 바랄게~";
            case "sad":
                return "꿈은 우리가 일상에서 겪은 감정들을 처리하는 역할을 한대!\n슬픈 꿈은 마음속 감정을 해소하는 과정이니, 자신에게\n 좀 더 따뜻하게 대해줘~";
            case "gloomy":
                return "요즘 유독 힘들게 느껴지는 일들이 많을까?\n꿈에서 느꼈던 우울함은 지나가는 감정이니, 밝은 현실에서 \n스스로를 다독여줘~";
            case "weird":
                return "요즘은 당황스러운 꿈을 많이 꿨네!\n이상한 꿈은 그저 상상 속의 일들이니까 너무 깊게 생각하지\n말고 가볍게 넘겨도 괜찮아~";
            case "comfort":
                return "일상이 편안한 것처럼 꿈도 편안한 요즘이구나!\n편안한 꿈에서의 평화로운 느낌을 오래 간직하며 \n하루를 시작해봐~";
            case "sweet":
                return "꿈은 특정 감정들을 꿈에서 비유적, 상징적으로 나타난다고 해!\n꿈에서의 설렘이 현실에서도 기분 좋은 \n기대감으로 이어지길 바라~";
            case "scary":
                return "요즘 꿈을 꾸는게 두려울 것 같아...\n두려움은 현실이 아니니까 안심하고, \n깨어난 지금은 안전하다는 걸 기억해~";
            default:
                return "알 수 없는 감정입니다."; // 기본값
        }
    }
}
