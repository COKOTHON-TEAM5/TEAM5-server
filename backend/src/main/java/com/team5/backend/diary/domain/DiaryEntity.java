package com.team5.backend.diary.domain;

import com.team5.backend.diary.dto.Emotion;
import com.team5.backend.member.domain.MemberEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @Setter
    private MemberEntity member;

    @Setter
    private LocalDate date;

    private LocalDateTime sleepTime;

    @Column(nullable = true)
    @Setter
    private LocalDateTime wakeupTime;

    @Column(nullable = true)
    @Setter
    private Emotion emotion;

    @Column(nullable = true)
    @Setter
    private String title;

    @Column(nullable = true)
    @Setter
    private String content;

}
