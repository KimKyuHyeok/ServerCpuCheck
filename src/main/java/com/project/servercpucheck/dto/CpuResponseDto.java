package com.project.servercpucheck.dto;

import com.project.servercpucheck.entity.Cpu;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CpuResponseDto {

    private LocalDateTime start;
    private LocalDateTime end;
    private double avg;
    private double max;
    private double min;
}
