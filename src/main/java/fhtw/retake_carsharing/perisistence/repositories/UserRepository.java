package fhtw.retake_carsharing.perisistence.repositories;

import fhtw.retake_carsharing.perisistence.entities.User;
import fhtw.retake_carsharing.perisistence.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByToken(String token);
    List<User> findByUserRole(UserRole role);
}
