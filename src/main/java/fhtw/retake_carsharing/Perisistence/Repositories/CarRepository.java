package fhtw.retake_carsharing.Perisistence.Repositories;

import fhtw.retake_carsharing.Perisistence.Entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    public Optional<Car> findByToken(String token);
}
