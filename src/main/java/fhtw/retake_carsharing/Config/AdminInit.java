package fhtw.retake_carsharing.Config;

import fhtw.retake_carsharing.perisistence.entities.User;
import fhtw.retake_carsharing.perisistence.entities.UserRole;
import fhtw.retake_carsharing.perisistence.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminInit {

    @Bean
    CommandLineRunner createAdmin(UserRepository userRepo) {
        return args -> {
            if (userRepo.findByUsername("admin").isPresent()) {
                return;
            }
            User admin = new User();
            admin.setFirstname("Admin");
            admin.setLastname("Admin");
            admin.setAge(30L);
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setCreditCardNr("0000");
            admin.setDriversLicenceNr("0000");
            admin.setUserRole(UserRole.ADMIN);
            userRepo.save(admin);
        };
    }
}