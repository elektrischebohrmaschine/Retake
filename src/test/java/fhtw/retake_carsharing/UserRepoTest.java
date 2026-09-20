package fhtw.retake_carsharing;

import fhtw.retake_carsharing.Perisistence.Entities.User;
import fhtw.retake_carsharing.Perisistence.Entities.UserRole;
import fhtw.retake_carsharing.Perisistence.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserRepoTest {

    @Autowired
    private UserRepository userRepo;

    @Test
    void findByUsername_shouldReturnExistingUser(){
        // Arrange
        User user = new User();
        user.setFirstname("Tyler");
        user.setLastname("the Creator");
        user.setAge(25L);
        user.setUsername("ttC");
        user.setPassword("1234");
        user.setCreditCardNr("1111");
        user.setDriversLicenceNr("A123");
        user.setUserRole(UserRole.CUSTOMER);
        userRepo.save(user);

        // Act
        Optional<User> potUser= userRepo.findByUsername("ttC");

        // Assert
        assertTrue(potUser.isPresent());
        assertEquals("ttC", potUser.get().getUsername());
    }

    @Test
    void findByUsername_shouldNotReturnExistingUser(){
        // Arrange
        User user = new User();
        user.setFirstname("Tyler");
        user.setLastname("the Creator");
        user.setAge(25L);
        user.setUsername("ttC");
        user.setPassword("1234");
        user.setCreditCardNr("1111");
        user.setDriversLicenceNr("A123");
        user.setUserRole(UserRole.CUSTOMER);
        userRepo.save(user);

        // Act
        Optional<User> potUser= userRepo.findByUsername("jean");

        // Assert
        assertTrue(potUser.isEmpty());
    }

}
