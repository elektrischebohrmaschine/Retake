package fhtw.retake_carsharing.perisistence.repositories;

import fhtw.retake_carsharing.perisistence.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    public Optional<Car> findByToken(String token);
}
