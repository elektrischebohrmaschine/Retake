package fhtw.retake_carsharing.Perisistence.Repositories;

import fhtw.retake_carsharing.Perisistence.Entities.User;
import fhtw.retake_carsharing.Perisistence.Entities.UserRole;
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
