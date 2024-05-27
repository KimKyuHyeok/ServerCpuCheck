package com.project.servercpucheck.controller;

import com.project.servercpucheck.dto.CpuResponseDto;
import com.project.servercpucheck.entity.Cpu;
import com.project.servercpucheck.service.CpuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CpuApiController.class)
class CpuApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CpuService cpuService;

    @DisplayName("[API] 분 단위 조회")
    @Test
    void testGetMinute() throws Exception {
        String start = "2024-05-26T00:00";
        String end = "2024-05-27T00:00";


        List<Cpu> list = List.of(new Cpu(), new Cpu());
        given(cpuService.minuteCheck(LocalDateTime.parse(start), LocalDateTime.parse(end)))
                .willReturn(list);

        mockMvc.perform(get("/api/v1/minute")
                .param("start", start)
                .param("end", end))
                .andExpect(status().isOk());
    }

    @DisplayName("[API] 시 단위 조회")
    @Test
    void testGetHour() throws Exception {
        String start = "2024-05-26T00:00";
        String end = "2024-05-27T00:00";


        List<CpuResponseDto> list = List.of(new CpuResponseDto(), new CpuResponseDto());
        given(cpuService.hourCheck(LocalDateTime.parse(start), LocalDateTime.parse(end)))
                .willReturn(list);

        mockMvc.perform(get("/api/v1/hour")
                        .param("start", start)
                        .param("end", end))
                .andExpect(status().isOk());
    }

    @DisplayName("[API] 월 단위 조회")
    @Test
    void testGetMonth() throws Exception {
        String start = "2024-05-26T00:00";
        String end = "2024-10-27T00:00";


        List<CpuResponseDto> list = List.of(new CpuResponseDto(), new CpuResponseDto());
        given(cpuService.monthCheck(LocalDateTime.parse(start), LocalDateTime.parse(end)))
                .willReturn(list);

        mockMvc.perform(get("/api/v1/month")
                        .param("start", start)
                        .param("end", end))
                .andExpect(status().isOk());
    }

}