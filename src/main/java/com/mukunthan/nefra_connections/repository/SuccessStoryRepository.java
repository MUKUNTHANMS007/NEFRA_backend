// File: repository/SuccessStoryRepository.java
package com.mukunthan.nefra_connections.repository;
import com.mukunthan.nefra_connections.entity.SuccessStory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SuccessStoryRepository extends JpaRepository<SuccessStory, Long> {
    List<SuccessStory> findByFeaturedTrue();
}