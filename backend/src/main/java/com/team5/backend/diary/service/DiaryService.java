package com.team5.backend.diary.service;

import com.team5.backend.diary.domain.DiaryEntity;
import com.team5.backend.diary.dto.*;
import com.team5.backend.diary.repository.DiaryRepository;
import com.team5.backend.exception.BadRequestException;
import com.team5.backend.exception.NotFoundException;
import com.team5.backend.member.domain.MemberEntity;
import com.team5.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        MemberEntity test = getMember(username);
        System.out.println(test.getUsername() + " startSleep: " + getMember(username).getStatus());

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

        MemberEntity test = getMember(username);
        System.out.println(test.getUsername() + " endSleep: " + getMember(username).getStatus());
    }

    public void skipDiary(String username) {
        MemberEntity member = getMember(username);

        DiaryEntity diary = getLatestDiary(member);
        member.setStatus(0);

        diaryRepository.save(diary);
        memberRepository.save(member);

        MemberEntity test = getMember(username);
        System.out.println(test.getUsername() + " skipSleep: " + getMember(username).getStatus());
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

        member.setStatus(0);

        diaryRepository.save(diary);
        memberRepository.save(member);

        MemberEntity test = getMember(username);
        System.out.println(test.getUsername() + " writeSleep: " + getMember(username).getStatus());
    }


    public MonthlyDiaryResponse monthlyDiary(String username, int year, int month) {
        MemberEntity member = getMember(username);

        if (month < 1 || month > 12) {
            throw new BadRequestException("month is between 1 and 12");
        }

        List<MonthlyDiaryEntry> diaries = getDiariesByYearAndMonth(member, year, month);
        return MonthlyDiaryResponse.builder().diaries(diaries).build();

    }


    private MemberEntity getMember(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private DiaryEntity getLatestDiary(MemberEntity member) {
        return member.getDiaries().stream()
                .max(Comparator.comparing(DiaryEntity::getSleepTime))
                .orElse(null); // 또는 Optional<Diary>로 반환할 수 있음
    }

    public List<DiaryEntity> getLatest30Diaries(MemberEntity member) {
        List<DiaryEntity> diaries = member.getDiaries();

        // 최신순으로 정렬한 후, emotion이 null이 아닌 다이어리 중 상위 30개 가져오기
        return diaries.stream()
                .filter(diary -> diary.getEmotion() != null) // emotion이 null이 아닌 다이어리만 필터링
                .sorted((d1, d2) -> d2.getDate().compareTo(d1.getDate())) // date로 내림차순 정렬
                .limit(30) // 상위 30개만 선택
                .collect(Collectors.toList()); // 결과를 List로 변환
    }

    public ReportResponse reportDiary(String username) {
        MemberEntity member = getMember(username);
        List<DiaryEntity> diaries = getLatest30Diaries(member);
        ReportResponse reportResponse = new ReportResponse();

        int max = 0;
        Emotion maxemotion = null;

        // 각 감정별 카운트를 계산
        for (Emotion emotion : Emotion.values()) {
            long count = diaries.stream()
                    .filter(diary -> diary.getEmotion() == emotion) // 감정 필터링
                    .count(); // 해당 감정의 카운트 계산

            if (((int)count) > max) {
                max = (int)count;
                maxemotion = emotion; // 감정 설명 가져오기

            }
        }
        if (max == 0) {
            reportResponse.setEmotion(""); // 가장 많이 등장한 감정
            reportResponse.setMaxcount(0); // 해당 감정의 카운트
            reportResponse.setContent("");
            reportResponse.setStatus(member.getStatus());
        }

        else{
            reportResponse.setEmotion(maxemotion.getDescription()); // 가장 많이 등장한 감정
            reportResponse.setMaxcount(max); // 해당 감정의 카운트
            reportResponse.setContent(Emotion.getContent(maxemotion.toString()));
            reportResponse.setStatus(member.getStatus());
        }
        return reportResponse;
    }

    private List<MonthlyDiaryEntry> getDiariesByYearAndMonth(MemberEntity member, int year, int month) {
        List<MonthlyDiaryEntry> filteredDiaries = new ArrayList<>();

        for (DiaryEntity diary : member.getDiaries()) {
            if (diary.getDate().getYear() == year && diary.getDate().getMonthValue() == month && diary.getEmotion() != null) {
                MonthlyDiaryEntry response = MonthlyDiaryEntry.builder()
                        .id(diary.getId())
                        .date(diary.getDate())
                        .sleepTime(diary.getSleepTime())
                        .wakeupTime(diary.getWakeupTime())
                        .emotion(diary.getEmotion().getDescription())
                        .title(diary.getTitle())
                        .content(diary.getContent() == null ? "" : diary.getContent())
                        .build();

                filteredDiaries.add(response);
            }
        }

        return filteredDiaries;
    }

}
