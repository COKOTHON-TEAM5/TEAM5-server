package com.team5.backend.diary.repository;

import com.team5.backend.diary.domain.DiaryEntity;
import com.team5.backend.member.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    Optional<DiaryEntity> findByMemberAndDate(MemberEntity member, LocalDate date);

}
