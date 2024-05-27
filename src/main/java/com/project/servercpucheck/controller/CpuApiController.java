package com.project.servercpucheck.controller;

import com.project.servercpucheck.dto.CpuResponseDto;
import com.project.servercpucheck.entity.Cpu;
import com.project.servercpucheck.repository.CpuRepository;
import com.project.servercpucheck.service.CpuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
            ) {
        return cpuservice.minuteCheck(start, end);
    }

    @GetMapping("/hour")
    public List<CpuResponseDto> hour(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    ) {
        return cpuservice.hourCheck(start, end);
    }

    @GetMapping("/month")
    public List<CpuResponseDto> month(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    ) {
        return cpuservice.monthCheck(start, end);
    }
}
