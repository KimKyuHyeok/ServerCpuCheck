package com.project.servercpucheck.controller;

import com.project.servercpucheck.dto.CpuResponseDto;
import com.project.servercpucheck.entity.Cpu;
import com.project.servercpucheck.service.CpuService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CpuApiController {

    private final CpuService cpuservice;

    @GetMapping("/minute")
    public List<Cpu> minute(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) throws Exception {
        validateDates(start, end);
        LocalDateTime now = LocalDateTime.now();
        if (!now.minusDays(7).isBefore(start)) {
            throw new Exception("최근 1주 데이터만 제공이 가능합니다.");
        }

        List<Cpu> result = cpuservice.minuteCheck(start, end);
        if (result.size() == 0) {
            throw new Exception("조회하신 기간 중 데이터가 존재하지 않습니다.");
        }

        return result;
    }

    @GetMapping("/hour")
    public List<CpuResponseDto> hour(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) throws Exception {
        validateDates(start, end);
        LocalDateTime now = LocalDateTime.now();
        if (!now.minusMonths(3).isBefore(start)) {
            throw new Exception("최근 3개월 데이터만 제공이 가능합니다.");
        }

        List<CpuResponseDto> result = cpuservice.hourCheck(start, end);
        if (result.size() == 0) {
            throw new Exception("조회하신 기간 중 데이터가 존재하지 않습니다.");
        }

        return result;
    }

    @GetMapping("/month")
    public List<CpuResponseDto> month(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) throws Exception {
        validateDates(start, end);
        LocalDateTime now = LocalDateTime.now();
        if (!now.minusYears(1).isBefore(start)) {
            throw new Exception("최근 1년 데이터만 제공이 가능합니다.");
        }
        List<CpuResponseDto> result = cpuservice.monthCheck(start, end);
        if (result.size() == 0) {
            throw new Exception("조회하신 기간 중 데이터가 존재하지 않습니다.");
        }
        return result;
    }

    private void validateDates(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("기간이 입릭되지 않았거나 옳바르지 않습니다.");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("조회 기간이 옳바르지 않습니다.");
        }
    }
}
