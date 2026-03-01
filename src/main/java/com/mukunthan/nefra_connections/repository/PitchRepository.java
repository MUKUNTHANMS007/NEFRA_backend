package com.mukunthan.nefra_connections.repository; // Ensure this is exactly .repository

import com.mukunthan.nefra_connections.entity.Pitch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PitchRepository extends JpaRepository<Pitch, Long> {
    // This allows the Controller to use .findAll() and .save()
}