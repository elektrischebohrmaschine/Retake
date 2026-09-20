package fhtw.retake_carsharing.Controller;


import fhtw.retake_carsharing.Perisistence.Entities.User;
import fhtw.retake_carsharing.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController (UserService userService){
        this.userService= userService;
    }

    private String[] decodeBasicAuth(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return null;
        }
        try {
            byte[] bytes = Base64.getDecoder().decode(authHeader.substring(6));
            String[] parts = new String(bytes, StandardCharsets.UTF_8).split(":", 2);
            return parts.length == 2 ? parts : null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register (@Valid @RequestBody User newUser){
        Optional<User> potNewUser = userService.signUp(newUser);
        if(potNewUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        User registeredUser= potNewUser.get();
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/session/login")
    public ResponseEntity<String> login(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String[] credentials = decodeBasicAuth(authHeader);
        if (credentials == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<User> potUser = userService.login(credentials[0], credentials[1]);
        if (potUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(potUser.get().getToken(), HttpStatus.OK);
    }

    @PostMapping("/session/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) {

        if (!userService.logout(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/drivers")
    public ResponseEntity<List<User>> listOfDrivers (@RequestHeader(value = "Authorization", required = false) String token){
        if (userService.authenticate(token).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(userService.findAllCostumer(), HttpStatus.OK);
    }

    @PutMapping("/drivers/{id}")
    public ResponseEntity<User> updateDriver(
            @PathVariable Long id,
            @RequestBody User updatedUser,
            @RequestHeader(value = "Authorization", required = false) String token) {

        Optional<User> potUser = userService.updateUser(id, updatedUser, token);
        if (potUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(potUser.get(), HttpStatus.OK);
    }
}
