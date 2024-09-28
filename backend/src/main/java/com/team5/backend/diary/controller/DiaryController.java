package com.team5.backend.diary.controller;

import com.team5.backend.diary.dto.DiaryRequest;
import com.team5.backend.diary.dto.TimeRecordRequest;
import com.team5.backend.diary.service.DiaryService;
import com.team5.backend.response.StatusResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;


    @PostMapping("sleep/start")
    public StatusResponse startSleep(@RequestAttribute("username") String username,
                                     @RequestBody @Valid TimeRecordRequest request) {
        diaryService.startSleep(username, request);
        return StatusResponse.of(200);
    }

    @PostMapping("sleep/end")
    public StatusResponse endSleep(@RequestAttribute("username") String username,
                                     @RequestBody @Valid TimeRecordRequest request) {
        diaryService.endSleep(username, request);
        return StatusResponse.of(200);
    }

    @PostMapping("skip")
    public StatusResponse skipDiary(@RequestAttribute("username") String username,
                                   @RequestBody @Valid TimeRecordRequest request) {
        diaryService.skipDiary(username, request);
        return StatusResponse.of(200);
    }

    @PostMapping("write")
    public StatusResponse writeDiary(@RequestAttribute("username") String username,
                                    @RequestBody @Valid DiaryRequest request) {
        diaryService.writeDiary(username, request);
        return StatusResponse.of(200);
    }

}
