package com.user_api.services;

import com.user_api.DTOs.RequestUser;
import com.user_api.exceptions.UserAlreadyExistsException;
import com.user_api.exceptions.UserListIsEmptyException;
import com.user_api.exceptions.UserNotFoundException;
import com.user_api.model.entities.Address;
import com.user_api.model.entities.User;
import com.user_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;

    //save new user
    public User saveNewUser(RequestUser data) {
       Address address = addressService.getAddress(data.cep());
       addressService.saveAddress(address);

       User newUser = new User(data.username(), data.password(), data.name(), data.email(), address);

       if (userRepository.existsByUsername(newUser.getUsername())) {
           throw new UserAlreadyExistsException("User Already Exists");
       }

       userRepository.save(newUser);
       return newUser;
    }
    //find all users
    public List<User> findAllUsers() {
        if (userRepository.findAll().isEmpty()) {
            throw new UserListIsEmptyException("Haven't Users for to List");
        }
        return userRepository.findAll();
    }
    //find user
    public User findUserByUsername(String username) {
        return getUserByUsername(username);
    }
    // delete user
    public void deleteUserByUsername(String username) {
        userRepository.deleteUserByUsername(getUserByUsername(username).getUsername());
    }
    //update user
    public User updateUserByUserName(String username, RequestUser data) {
        User user = getUserByUsername(username);
        user.setName(data.name());
        user.setUsername(data.username());
        user.setPassword(data.password());
        user.setEmail(data.email());
        user.setAddress(addressService.getAddress(data.cep()));
        return user;
    }
    private User getUserByUsername(String username) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findUserByUsername(username));
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        return optionalUser.get();
    }

}
