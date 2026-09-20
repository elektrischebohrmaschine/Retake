package fhtw.retake_carsharing.Controller;

import fhtw.retake_carsharing.Messages.TrackingRequest;
import fhtw.retake_carsharing.Perisistence.Entities.User;
import fhtw.retake_carsharing.Service.CarService;
import fhtw.retake_carsharing.Service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static fhtw.retake_carsharing.Config.RabbitMQConfig.CAR_STATUS_QUEUE;
import static fhtw.retake_carsharing.Config.RabbitMQConfig.GEN_REPORT_QUEUE;

@RestController
@RequestMapping("/api")
public class ReportController {

    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;

    public ReportController(UserService userService, RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/reports/{userId}")
    public ResponseEntity<Void> createReport(@PathVariable Long userId, @RequestHeader(value = "Authorization", required = false) String userToken) {
        Optional<User> potAdmin = userService.authenticateAdmin(userToken);
        if (potAdmin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<User> potUser = userService.findByUserId(userId);
        if (potUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TrackingRequest report = new TrackingRequest(userId);
        rabbitTemplate.convertAndSend(GEN_REPORT_QUEUE, report);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }
}