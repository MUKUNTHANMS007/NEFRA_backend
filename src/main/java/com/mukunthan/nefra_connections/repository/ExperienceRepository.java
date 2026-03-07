package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByUserIdOrderByStartYearDesc(Long userId);
}