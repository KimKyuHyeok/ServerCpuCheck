package com.project.servercpucheck.service;

import com.project.servercpucheck.dto.CpuResponseDto;
import com.project.servercpucheck.entity.Cpu;
import com.project.servercpucheck.repository.CpuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@RequiredArgsConstructor
@Service
public class CpuService {


    private final CpuRepository cpuRepository;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

//    @PostConstruct
//    public void init() {
//        cpuCheck();
//    }
//
//    public void cpuCheck() {
//        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//
//        Runnable cpuCheckTask = new Runnable() {
//            @Override
//            public void run() {
//                double systemCpuLoad = osBean.getSystemCpuLoad() * 100;
//
//                String cpuUsage = String.format("%.2f", systemCpuLoad);
//                System.out.println("[ TEST ] : " + cpuUsage);
//
//                Cpu cpu = new Cpu();
//                cpu.setUsage(cpuUsage);
//
//                cpuRepository.save(cpu);
//
//            }
//        };
//
//        scheduler.scheduleAtFixedRate(cpuCheckTask, 0, 1, TimeUnit.MINUTES);
//    }

    public List<Cpu> minuteCheck(LocalDateTime start, LocalDateTime end) {

        return cpuRepository.findByCreatedAtBetween(start, end);
    }



    public List<CpuResponseDto> hourCheck(LocalDateTime start, LocalDateTime end) {

        LocalDateTime date = start;
        List<CpuResponseDto> list = new ArrayList<>();

        while (!date.isAfter(end)) {
            List<Cpu> cpuList = minuteCheck(date, date.plusHours(1));

            CpuResponseDto dto = CpuResponseDto.builder()
                    .start(date)
                    .end(date.plusHours(1))
                    .avg(usageAverage(cpuList))
                    .max(usageMax(cpuList))
                    .min(usageMin(cpuList))
                    .build();

            list.add(dto);

            date = date.plusHours(1);
        }

        return list;
    }

    public List<CpuResponseDto> monthCheck(LocalDateTime start, LocalDateTime end) {

        LocalDateTime date = start;
        List<CpuResponseDto> list = new ArrayList<>();

        while (!date.isAfter(end)) {
            List<Cpu> cpuList = minuteCheck(date, date.plusMonths(1));

            CpuResponseDto dto = CpuResponseDto.builder()
                    .start(date)
                    .end(date.plusMonths(1))
                    .avg(usageAverage(cpuList))
                    .max(usageMax(cpuList))
                    .min(usageMin(cpuList))
                    .build();

            list.add(dto);

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
