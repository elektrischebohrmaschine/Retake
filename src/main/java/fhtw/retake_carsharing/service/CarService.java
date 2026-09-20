package fhtw.retake_carsharing.service;


import fhtw.retake_carsharing.perisistence.entities.Car;
import fhtw.retake_carsharing.perisistence.entities.CarStatus;
import fhtw.retake_carsharing.perisistence.entities.User;
import fhtw.retake_carsharing.perisistence.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarService {

    private final CarRepository carRepo;

    public CarService(CarRepository carRepo){
        this.carRepo= carRepo;
    }

    public List<Car> findAllCars(){
        return carRepo.findAll();
    }

    public Optional<Car> findById(Long Id){
        return carRepo.findById(Id);
    }
    public Car saveCar(Car car){
        return carRepo.save(car);
    }


    public Car createCar (Car newCar){
        newCar.setCarID(null);
        newCar.setCurrentUser(null);
        newCar.setStatus(CarStatus.FREE);
        String newToken = UUID.randomUUID().toString();
        newCar.setToken(newToken);
        return carRepo.save(newCar);
    }

    public Optional<Car> updateCar(Long id, Car updatedCar) {
        Optional<Car> potCar = carRepo.findById(id);
        if (potCar.isEmpty()) {
            return Optional.empty();
        }
        Car currentCar = potCar.get();

        if (updatedCar.getCarName() != null && !updatedCar.getCarName().isBlank()) {
            currentCar.setCarName(updatedCar.getCarName());
        }
        if (updatedCar.getDescription() != null && !updatedCar.getDescription().isBlank()) {
            currentCar.setDescription(updatedCar.getDescription());
        }
        if (updatedCar.getStatus() != null) {
            currentCar.setStatus(updatedCar.getStatus());
        }
        if (updatedCar.getCoordinates() != null) {
            currentCar.setCoordinates(updatedCar.getCoordinates());
        }

        return Optional.of(carRepo.save(currentCar));
    }

    public boolean deleteCar (Long id){
        Optional<Car> potCar = carRepo.findById(id);
        if (potCar.isEmpty()){
            return false;
        }
        Car deletedCar = potCar.get();
        carRepo.delete(deletedCar);
        return true;
    }
}
