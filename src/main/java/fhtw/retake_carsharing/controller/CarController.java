package fhtw.retake_carsharing.controller;


import fhtw.retake_carsharing.perisistence.entities.Car;
import fhtw.retake_carsharing.perisistence.entities.User;
import fhtw.retake_carsharing.service.CarService;
import fhtw.retake_carsharing.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarController {

    private final CarService carService;
    private final UserService userService;

    public CarController(CarService carService, UserService userService){
        this.carService= carService;
        this.userService= userService;
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> registerCar(@Valid @RequestBody Car newCar, @RequestHeader(value = "Authorization", required = false) String userToken){
        Optional<User> Admin = userService.authenticateAdmin(userToken);
        if (Admin.isEmpty()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>( carService.createCar(newCar), HttpStatus.CREATED);
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> listOfCars (@RequestHeader(value = "Authorization", required = false) String userToken){
        Optional<User> admin = userService.authenticateAdmin(userToken);
        if (admin.isEmpty()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(carService.findAllCars(), HttpStatus.OK);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> showCar (@RequestHeader(value = "Authorization", required = false) String userToken, @PathVariable Long id){
        Optional<User> admin = userService.authenticateAdmin(userToken);
        if (admin.isEmpty()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Car> potCar = carService.findById(id);
        if(potCar.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Car foundCar = potCar.get();
        return new ResponseEntity<>(foundCar, HttpStatus.OK);
    }

    @PatchMapping("/car/{id}")
    public ResponseEntity<Car> updateCar (@PathVariable Long id,
                                          @RequestBody Car updatedCar,
                                          @RequestHeader(value = "Authorization", required = false) String userToken) {
        Optional<User> admin = userService.authenticateAdmin(userToken);
        if (admin.isEmpty()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Car> potCar = carService.updateCar(id, updatedCar);
        if(potCar.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(potCar.get(), HttpStatus.OK);
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<Void> deleteCar (@PathVariable Long id,@RequestHeader(value = "Authorization", required = false) String userToken) {
        Optional<User> admin = userService.authenticateAdmin(userToken);
        if (admin.isEmpty()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        boolean deletedCar= carService.deleteCar(id);
        if(!deletedCar){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//    o DELETE /api/car/{id} – remove the car of the id
}
