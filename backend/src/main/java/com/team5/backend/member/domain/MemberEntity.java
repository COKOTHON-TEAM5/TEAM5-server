package com.team5.backend.member.domain;

import com.team5.backend.diary.domain.DiaryEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryEntity> diaries;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Setter
    @Column(nullable = false) //수면상태 (보류)
    private int status = 0;


    public void addDiary(DiaryEntity diary) {
        this.diaries.add(diary);
        diary.setMember(this);
    }

}