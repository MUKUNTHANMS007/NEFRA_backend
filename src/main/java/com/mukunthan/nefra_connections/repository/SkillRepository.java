package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByUserId(Long userId);
}