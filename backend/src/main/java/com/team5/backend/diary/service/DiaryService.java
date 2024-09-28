package com.team5.backend.diary.service;

import com.team5.backend.diary.domain.DiaryEntity;
import com.team5.backend.diary.dto.DiaryRequest;
import com.team5.backend.diary.dto.Emotion;
import com.team5.backend.diary.dto.TimeRecordRequest;
import com.team5.backend.diary.repository.DiaryRepository;
import com.team5.backend.exception.BadRequestException;
import com.team5.backend.exception.NotFoundException;
import com.team5.backend.member.domain.MemberEntity;
import com.team5.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;


    public void startSleep(String username, TimeRecordRequest request) {
        MemberEntity member = getMember(username);

        DiaryEntity diary = DiaryEntity.builder()
                .sleepTime(request.getTime())
                .build();

        member.addDiary(diary);
        diary.setDate(LocalDate.of(2000, 1, 1));
        member.setStatus(1);

        diaryRepository.save(diary);
        memberRepository.save(member);
    }

    public void endSleep(String username, TimeRecordRequest request) {
        MemberEntity member = getMember(username);

        DiaryEntity diary = getLatestDiary(member);
        diary.setWakeupTime(request.getTime());
        diary.setDate(request.getTime().toLocalDate());

        member.addDiary(diary);
        member.setStatus(2);

        diaryRepository.save(diary);
        memberRepository.save(member);
    }

    public void skipDiary(String username) {
        MemberEntity member = getMember(username);

        DiaryEntity diary = getLatestDiary(member);
        member.setStatus(0);

        diaryRepository.save(diary);
        memberRepository.save(member);
    }

    public void writeDiary(String username, DiaryRequest request) {
        MemberEntity member = getMember(username);

        if (member.getStatus() != 2) {
            throw new BadRequestException("Sleep and wake up first");
        }

        DiaryEntity diary = getLatestDiary(member);

        if (Emotion.contains(request.getEmotion())) {
            diary.setEmotion(Emotion.valueOf(request.getEmotion()));
        } else {
            throw new BadRequestException("Emotion is wrong");
        }

        if (request.getTitle() == null) {
            diary.setTitle("오늘은 " + Emotion.valueOf(request.getEmotion()).getDescription() + " 꿈을 꾸었다.");
        } else {
            diary.setTitle(request.getTitle());
        }

        diary.setContent(request.getContent());

        diaryRepository.save(diary);

    }


    private MemberEntity getMember(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private DiaryEntity getLatestDiary(MemberEntity member) {
        return member.getDiaries().stream()
                .max((diary1, diary2) -> diary1.getDate().compareTo(diary2.getDate()))
                .orElse(null); // 또는 Optional<Diary>로 반환할 수 있음
    }

}
