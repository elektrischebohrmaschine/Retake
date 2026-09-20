package fhtw.retake_carsharing.Controller;


import fhtw.retake_carsharing.Messages.CarEmergencyUpdates;
import fhtw.retake_carsharing.Messages.CarStatusUpdates;
import fhtw.retake_carsharing.Perisistence.Entities.Car;
import fhtw.retake_carsharing.Service.CarService;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static fhtw.retake_carsharing.Config.RabbitMQConfig.CAR_STATUS_QUEUE;
import static fhtw.retake_carsharing.Config.RabbitMQConfig.EMERGENCY_QUEUE;

@RestController
@RequestMapping("/api")
public class CarStatusController {

    private final CarService carService;
    private final RabbitTemplate rabbitTemplate;

    public CarStatusController(CarService carService, RabbitTemplate rabbitTemplate) {
        this.carService = carService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/cars/{carId}/status")
    public ResponseEntity<Void> carStatus(@PathVariable Long carId, @Valid @RequestBody CarStatusUpdates carStatusU, @RequestHeader(value = "Authorization", required = false) String carToken) {
        Optional<Car> potCar = carService.authenticateCar(carId, carToken);
        if (potCar.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        carStatusU.setCarId(carId);
        rabbitTemplate.convertAndSend(CAR_STATUS_QUEUE, carStatusU);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cars/{carId}/alarm")
    public ResponseEntity<Void> carEmergency(@PathVariable Long carId, @Valid @RequestBody CarEmergencyUpdates carEmergencyU, @RequestHeader(value = "Authorization", required = false) String carToken) {
        Optional<Car> potCar = carService.authenticateCar(carId, carToken);
        if (potCar.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        carEmergencyU.getStatusUpdate().setCarId(carId);
        rabbitTemplate.convertAndSend(EMERGENCY_QUEUE, carEmergencyU);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}