package com.mukunthan.nefra_connections.config;

import com.mukunthan.nefra_connections.entity.Pitch;
import com.mukunthan.nefra_connections.entity.SuccessStory;
import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.repository.PitchRepository;
import com.mukunthan.nefra_connections.repository.UserRepository;
import com.mukunthan.nefra_connections.repository.SuccessStoryRepository; // FIX: Add this import
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            PitchRepository pitchRepository,
            SuccessStoryRepository successStoryRepository) { // Added Story Repo
        return args -> {

            // 1. SEED MUKUNTHAN (THE ENTREPRENEUR)
            // We use orElseGet to prevent duplicate email errors
            User mukunthan = userRepository.findByEmail("mukunthan@nefra.com")
                    .orElseGet(() -> userRepository.save(new User(
                            null, "Mukunthan", "mukunthan@nefra.com", "mukunthan123",
                            User.Role.ENTREPRENEUR, "NEFRA Connections", "Software",
                            "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d", true
                    )));

            // 2. SEED JENSEN (THE INVESTOR)
            userRepository.findByEmail("jensen@nvidia.com")
                    .orElseGet(() -> userRepository.save(new User(
                            null, "Jensen Huang", "jensen@nvidia.com", "nvidia123",
                            User.Role.INVESTOR, "NVIDIA", "AI Tech",
                            "https://www.nvidia.com/content/dam/en-zz/Solutions/about-nvidia/management/jensen-huang-nvidia-ceo-4c.jpg", true
                    )));

            // 3. SEED PITCHES (Check pitch count instead of user count)
            if (pitchRepository.count() == 0) {
                pitchRepository.save(new Pitch(
                        null, "AXTR_LABS", "AI driven lab automation", "HealthTech",
                        50000.0, "OPEN", mukunthan
                ));

                pitchRepository.save(new Pitch(
                        null, "EcoPSG", "Sustainable campus energy solution", "Energy",
                        25000.0, "OPEN", mukunthan
                ));
                System.out.println("✅ Pitches Seeded.");
            }

            // 4. SEED SUCCESS STORIES (Fixes empty Stories section)
            if (successStoryRepository.count() == 0) {
                successStoryRepository.save(new SuccessStory(
                        null, "Seed Round Success", "1.2M USD", "First campus-led investment",
                        "Mukunthan", "NEFRA", "https://images.unsplash.com/photo-1559136555-9303baea8ebd", true
                ));
                System.out.println("✅ Success Stories Seeded.");
            }

            System.out.println("🚀 NEFRA: Database Sync Complete.");
        };
    }
}