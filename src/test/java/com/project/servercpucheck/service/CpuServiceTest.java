package com.project.servercpucheck.service;

import com.project.servercpucheck.dto.CpuResponseDto;
import com.project.servercpucheck.entity.Cpu;
import com.project.servercpucheck.repository.CpuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CpuServiceTest {

    @Mock
    private CpuRepository cpuRepository;

    @InjectMocks
    private CpuService cpuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("[Service] 분 단위 조회")
    @Test
    void testMinuteCheck() {
        LocalDateTime start = LocalDateTime.of(2024, 5, 25, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 5, 26, 0, 0);
        List<Cpu> cpus = Collections.singletonList(new Cpu());

        when(cpuRepository.findByCreatedAtBetween(start, end)).thenReturn(cpus);

        List<Cpu> cpuList = cpuService.minuteCheck(start, end);

        assertEquals(cpus, cpuList);
    }

    @DisplayName("[Service] 시 단위 조회")
    @Test
    void testHourCheck() {
        LocalDateTime start = LocalDateTime.of(2024, 5, 26, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 5, 27, 0, 0);

        List<Cpu> fakeCpuList = new ArrayList<>();

        when(cpuService.minuteCheck(start, end)).thenReturn(fakeCpuList);

        List<CpuResponseDto> result = cpuService.hourCheck(start, end);

        assertNotNull(result.size());
    }

    @DisplayName("[Service] 월 단위 조회")
    @Test
    void testMonthCheck() {
        LocalDateTime start = LocalDateTime.of(2024, 5, 26, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 10, 27, 0, 0);


        List<Cpu> fakeCpuList = new ArrayList<>();

        when(cpuService.minuteCheck(start, end)).thenReturn(fakeCpuList);

        List<CpuResponseDto> result = cpuService.monthCheck(start, end);

        assertNotNull(result.size());
    }

}