package com.user_api.services;

import com.user_api.DTOs.RequestUserDTO;
import com.user_api.DTOs.ResponseAllUsersDTO;
import com.user_api.DTOs.ResponseUserDTO;
import com.user_api.exceptions.UserEmailAlreadyExistsException;
import com.user_api.exceptions.UsernameAlreadyExistsException;
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
    public ResponseUserDTO saveNewUser(RequestUserDTO data) {
       Address address = addressService.getAddress(data.cep());
       addressService.saveAddress(address);

       User newUser = new User(data.username(), data.password(), data.name(), data.email(), address);

       if (userRepository.existsByUsername(newUser.getUsername())) {
           throw new UsernameAlreadyExistsException("User Already Exists");
       }
       if(userRepository.existsByEmail(newUser.getEmail())){
           throw new UserEmailAlreadyExistsException("Email Already in use");
       }

       userRepository.save(newUser);
       return new ResponseUserDTO(newUser.getName(), newUser.getEmail(), newUser.getAddress());
    }
    //find all users
    public ResponseAllUsersDTO findAllUsers() {
        List<ResponseUserDTO> userList = userRepository.findAll().stream().map(x -> new ResponseUserDTO(x.getName(), x.getEmail(), x.getAddress())).toList();
        if (userList.isEmpty()) {
            throw new UserListIsEmptyException("Haven't Users for to List");
        }
        return new ResponseAllUsersDTO(userList);
    }
    //find user
    public ResponseUserDTO findUserByUsername(String username) {
        User user = getUserByUsername(username);
        return new ResponseUserDTO(user.getName(), user.getEmail(), user.getAddress());
    }
    // delete user
    public void deleteUserByUsername(String username) {
        userRepository.deleteUserByUsername(getUserByUsername(username).getUsername());
    }
    //update user
    public ResponseUserDTO updateUserByUserName(String username, RequestUserDTO data) {
        User user = getUserByUsername(username);
        user.setName(data.name());
        user.setUsername(data.username());
        user.setPassword(data.password());
        user.setEmail(data.email());
        user.setAddress(addressService.getAddress(data.cep()));
        return new ResponseUserDTO(user.getName(), user.getEmail(), user.getAddress());
    }
    private User getUserByUsername(String username) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findUserByUsername(username));
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        return optionalUser.get();
    }

}
