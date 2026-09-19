package fhtw.retake_carsharing.service;


import fhtw.retake_carsharing.perisistence.entities.User;
import fhtw.retake_carsharing.perisistence.entities.UserRole;
import fhtw.retake_carsharing.perisistence.repositories.UserRepository;
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

    public Optional<User> findByUserId (Long id){
        return userRepo.findById(id);
    }

    public User save(User user){
        return userRepo.save(user);
    }

    public Optional<User> authenticate (String token){
        if (token == null || token.isBlank()){
            return Optional.empty();}

        return userRepo.findByToken(token);
    }

    public Optional<User> signUp(User newUser){
        if(userRepo.findByUsername(newUser.getUsername()).isPresent()){
            return Optional.empty();
        }
        newUser.setUserRole(UserRole.CUSTOMER);
        newUser.setId(null);
        newUser.setToken(null);
        userRepo.save(newUser);
        return Optional.of(newUser);
    }

    public Optional<User> login(String username, String password){
        Optional<User> potUser = userRepo.findByUsername(username);
        if(potUser.isEmpty()){
            return Optional.empty();
        }
        User loggedUser = potUser.get();
        if(!loggedUser.getPassword().equals(password)){
            return Optional.empty();
        }
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

        currentUser.setFirstname(updatedUser.getFirstname());
        currentUser.setLastname(updatedUser.getLastname());
        currentUser.setAge(updatedUser.getAge());
        currentUser.setCreditCardNr(updatedUser.getCreditCardNr());
        currentUser.setDriversLicenceNr(updatedUser.getDriversLicenceNr());

        return Optional.of(userRepo.save(currentUser));
    }

}
