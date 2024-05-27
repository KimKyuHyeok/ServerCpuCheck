package com.project.servercpucheck.repository;

import com.project.servercpucheck.entity.Cpu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CpuRepository extends JpaRepository<Cpu, Long> {
    List<Cpu> findByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
