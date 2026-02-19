package com.mukunthan.nefra_connections.config;

import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.entity.SuccessStory;
import com.mukunthan.nefra_connections.repository.UserRepository;
import com.mukunthan.nefra_connections.repository.SuccessStoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, SuccessStoryRepository storyRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                // Seeding Users with email addresses
                userRepository.save(new User(null, "Mukunthan", "mukunthan@nefra.com", "Lead Developer", "NEFRA_Connections", "Software", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d", true));
                userRepository.save(new User(null, "Siddiq", "siddiq@axtr.com", "Founder", "Axtr Labs", "Hardware", "https://images.unsplash.com/photo-1500648767791-00dcc994a43e", true));
                userRepository.save(new User(null, "Jensen Huang", "jensen@nvidia.com", "CEO", "NVIDIA", "AI & Computing", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c4/Jensen_Huang_%2851453965902%29_%28cropped%29.jpg/640px-Jensen_Huang_%2851453965902%29_%28cropped%29.jpg", true));
                userRepository.save(new User(null, "Elon Musk", "elon@spacex.com", "Chief Engineer", "SpaceX / Tesla", "Aerospace", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Elon_Musk_Royal_Society_crop.jpg/640px-Elon_Musk_Royal_Society_crop.jpg", true));

                System.out.println("Database seeded with user accounts.");
            }

            if (storyRepository.count() == 0) {
                storyRepository.save(new SuccessStory(null, "AI Growth", "$2M Seed", "Scaled to 50k users", "Sarah Chen", "TechVentures", "https://images.unsplash.com/photo-1552664730-d307ca884978", true));
                storyRepository.save(new SuccessStory(null, "Green Energy", "$500k Grant", "Reduced waste by 40%", "David Park", "EcoFlow", "https://images.unsplash.com/photo-1550751827-4bd374c3f58b", true));

                System.out.println("Success stories seeded.");
            }
        };
    }
}