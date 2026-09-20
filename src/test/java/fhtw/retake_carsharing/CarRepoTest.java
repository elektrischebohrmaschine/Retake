package fhtw.retake_carsharing;

import fhtw.retake_carsharing.Perisistence.Entities.Car;
import fhtw.retake_carsharing.Perisistence.Entities.Coords;
import fhtw.retake_carsharing.Perisistence.Repositories.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CarRepoTest {

        @Autowired
        private CarRepository carRepo;

        @Test
        void findByToken_shouldReturnExistingCar(){
            // Arrange
            Car car = new Car();
            car.setToken("hi3");
            car.setCarName("Audo");
            car.setCoordinates(new Coords(128.7, 49.6));
            car.setDescription("v v pretty");
            carRepo.save(car);

            // Act
            Optional<Car> potCar= carRepo.findByToken("hi3");

            // Assert
            assertTrue(potCar.isPresent());
            assertEquals("hi3", potCar.get().getToken());
        }

        @Test
        void findByToken_shouldNotReturnExistingCar(){
            // Arrange
            Car car = new Car();
            car.setToken("hi3");
            car.setCarName("Audo");
            car.setCoordinates(new Coords(128.7, 49.6));
            car.setDescription("v v pretty");
            carRepo.save(car);

            // Act
            Optional<Car> potCar= carRepo.findByToken("jean");

            // Assert
            assertTrue(potCar.isEmpty());
        }

    }


