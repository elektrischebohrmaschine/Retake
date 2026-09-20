package fhtw.retake_carsharing.Service;


import fhtw.retake_carsharing.Perisistence.Entities.User;
import fhtw.retake_carsharing.Perisistence.Entities.UserRole;
import fhtw.retake_carsharing.Perisistence.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public List<User> findAllUser(){
        return userRepo.findAll();
    }

    public List<User> findAllCostumer(){
        return userRepo.findByUserRole(UserRole.CUSTOMER);
    }

    public Optional<User> authenticateAdmin(String token) {
        // is this an Admin?
        Optional<User> potAdmin = authenticate(token);
        if (potAdmin.isEmpty() || !potAdmin.get().isAdmin()) {
            return Optional.empty();
        }
        return potAdmin;
    }

    public Optional<User> findByUserId (Long id){
        return userRepo.findById(id);
    }

    public User save(User user){
        return userRepo.save(user);
    }

    public Optional<User> authenticate (String token){
        // token tells us who this is
        if (token == null || token.isBlank()){
            return Optional.empty();}

        return userRepo.findByToken(token);
    }

    public Optional<User> signUp(User newUser){
        if(userRepo.findByUsername(newUser.getUsername()).isPresent()){
            return Optional.empty(); // username already taken
        }
        newUser.setUserRole(UserRole.CUSTOMER);
        newUser.setId(null); // DB already does that for us
        newUser.setToken(null); // only at login
        userRepo.save(newUser);
        return Optional.of(newUser);
    }

    public Optional<User> login(String username, String password){
        Optional<User> potUser = userRepo.findByUsername(username);
        if(potUser.isEmpty()){
            return Optional.empty(); // username does not exist
        }
        User loggedUser = potUser.get();
        if(!loggedUser.getPassword().equals(password)){
            return Optional.empty();
        }
        // creating new token = one person can only be connected once
        String newToken = UUID.randomUUID().toString();
        loggedUser.setToken(newToken);
        userRepo.save(loggedUser);

        return Optional.of(loggedUser);
    }

    public boolean logout(String token){
        Optional<User> potUser =authenticate(token);
        if(potUser.isEmpty()){
            return false;
        }

        User loggedUser = potUser.get();
        loggedUser.setToken(null);
        userRepo.save(loggedUser);

        return true;
    }

    public Optional<User> updateUser(Long id, User updatedUser, String token) {
        Optional<User> potUser = authenticate(token);
        if (potUser.isEmpty()) {
            return Optional.empty();
        }

        User currentUser = potUser.get();
        if (!currentUser.getId().equals(id)) {
            return Optional.empty();
        }
        if (updatedUser.getFirstname() != null && !updatedUser.getFirstname().isBlank()) {
            currentUser.setFirstname(updatedUser.getFirstname());
        }
        if (updatedUser.getLastname() != null && !updatedUser.getLastname().isBlank()) {
            currentUser.setLastname(updatedUser.getLastname());
        }

        if (updatedUser.getAge() != null) {
            currentUser.setAge(updatedUser.getAge());
        }
        if (updatedUser.getCreditCardNr() != null && !updatedUser.getCreditCardNr().isBlank()) {
            currentUser.setCreditCardNr(updatedUser.getCreditCardNr());
        }

        if (updatedUser.getDriversLicenceNr() != null && !updatedUser.getDriversLicenceNr().isBlank()) {
            currentUser.setDriversLicenceNr(updatedUser.getDriversLicenceNr());
        }
        return Optional.of(userRepo.save(currentUser));
    }

}
