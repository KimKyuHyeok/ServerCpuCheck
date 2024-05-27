package com.project.servercpucheck.service;

import com.project.servercpucheck.dto.CpuResponseDto;
import com.project.servercpucheck.entity.Cpu;
import com.project.servercpucheck.repository.CpuRepository;
import com.sun.management.OperatingSystemMXBean;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class CpuService {

    private final CpuRepository cpuRepository;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void init() {
        try {
            cpuCheck();
        } catch (Exception e) {
            System.err.println("에러 발생 : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void cpuCheck() throws Exception {
        try {
            OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

            Runnable cpuCheckTask = new Runnable() {
                @Override
                public void run() {
                    double systemCpuLoad = osBean.getSystemCpuLoad() * 100;

                    String cpuUsage = String.format("%.2f", systemCpuLoad);

                    Cpu cpu = new Cpu();
                    cpu.setCpuUsage(cpuUsage);
                    cpu.setCreatedAt(LocalDateTime.now());

                    try {
                        cpuRepository.save(cpu);
                        System.out.println("[CPU] 사용량 : " + cpuUsage);
                    } catch (Exception e) {
                        System.err.println("CPU 저장 도중 에러가 발생하였습니다 : " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            };

            scheduler.scheduleAtFixedRate(cpuCheckTask, 0, 1, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new Exception("CPU 데이터를 수집하는 도중 에러가 발생하였습니다.");
        }

    }

    public List<Cpu> minuteCheck(LocalDateTime start, LocalDateTime end) {

        List<Cpu> test = cpuRepository.findByCreatedAtBetween(start, end);

        return cpuRepository.findByCreatedAtBetween(start, end);
    }



    public List<CpuResponseDto> hourCheck(LocalDateTime start, LocalDateTime end) {

        LocalDateTime date = start;
        List<CpuResponseDto> list = new ArrayList<>();

        while (!date.isAfter(end)) {
            List<Cpu> cpuList = minuteCheck(date, date.plusHours(1));

            if (cpuList.size() != 0) {
                CpuResponseDto dto = CpuResponseDto.builder()
                        .start(date)
                        .end(date.plusHours(1))
                        .avg(usageAverage(cpuList))
                        .max(usageMax(cpuList))
                        .min(usageMin(cpuList))
                        .build();

                list.add(dto);
            }
            date = date.plusHours(1);
        }

        return list;
    }

    public List<CpuResponseDto> monthCheck(LocalDateTime start, LocalDateTime end) {

        LocalDateTime date = start;
        List<CpuResponseDto> list = new ArrayList<>();

        while (!date.isAfter(end)) {
            List<Cpu> cpuList = minuteCheck(date, date.plusMonths(1));

            if (cpuList.size() != 0) {
                CpuResponseDto dto = CpuResponseDto.builder()
                        .start(date)
                        .end(date.plusMonths(1))
                        .avg(usageAverage(cpuList))
                        .max(usageMax(cpuList))
                        .min(usageMin(cpuList))
                        .build();

                list.add(dto);
            }
            date = date.plusMonths(1);
        }

        return list;
    }

    public double usageAverage(List<Cpu> dto) {
        double totalUsage = dto.stream()
                .mapToDouble(cpu -> {
                    return Double.parseDouble(cpu.getCpuUsage());
                }).sum();

        return totalUsage / dto.size();
    }

    public double usageMax(List<Cpu> dto) {
        double max = dto.stream()
                .mapToDouble(cpu -> {
                    return Double.parseDouble(cpu.getCpuUsage());
                }).max()
                .orElse(0.0);

        return max;
    }

    public double usageMin(List<Cpu> dto) {
        double min = dto.stream()
                .mapToDouble(cpu -> {
                    return Double.parseDouble(cpu.getCpuUsage());
                }).min()
                .orElse(0.0);

        return min;
    }
}
