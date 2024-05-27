package com.project.servercpucheck.repository;

import com.project.servercpucheck.entity.Cpu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CpuRepositoryTest {

    @Autowired
    private CpuRepository cpuRepository;

    @DisplayName("[Data] CPU 리스트 조회")
    @Test
    void testFindCpuByMinute() {
        LocalDateTime start = LocalDateTime.of(2024, 5, 24, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 5, 25, 0, 0);

        List<Cpu> cpus = cpuRepository.findByCreatedAtBetween(start, end);

        assertNotNull(cpus);
    }

}