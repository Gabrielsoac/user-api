package com.user_api.services.Impl;

import com.user_api.DTOs.RequestTelegramMessageDTO;
import com.user_api.DTOs.RequestUserDTO;
import com.user_api.DTOs.ResponseAllUsersDTO;
import com.user_api.DTOs.ResponseUserDTO;
import com.user_api.exceptions.UsernameAlreadyExistsException;
import com.user_api.exceptions.UserListIsEmptyException;
import com.user_api.exceptions.UserNotFoundException;
import com.user_api.infra.clients.TelegramClient;
import com.user_api.model.entities.Address;
import com.user_api.model.entities.User;
import com.user_api.repositories.UserRepository;
import com.user_api.services.AddressService;
import com.user_api.services.UserService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private AddressService addressService;
    private TelegramClient telegramClient;
    //private String chatId = "-1002169688714";

    public UserServiceImpl(UserRepository userRepository, AddressService addressService, TelegramClient telegramClient){
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.telegramClient = telegramClient;
    }

    //save new user
    public ResponseUserDTO createUser(RequestUserDTO data) {
        
        if (userRepository.existsByUsername(data.username())){
            throw new UsernameAlreadyExistsException("Username already Exists");
        }
        
        Address address = addressService.getAddress(data.cep());
        User newUser = new User(data.username(), data.name(), data.apresentation(), address);

        userRepository.save(newUser);

        ResponseUserDTO responseUserDTO =  new ResponseUserDTO(
        newUser.getUsername(),
        newUser.getName(),
        newUser.getApresentation(),
        newUser.getAddress().getCidade(),
        newUser.getAddress().getEstado());

        telegramClient.sendMessage(new RequestTelegramMessageDTO("-1002169688714", responseUserDTO.toString()));

        return responseUserDTO;

    }

    //find all users
    public ResponseAllUsersDTO readAllUsers() {
        List<ResponseUserDTO> userList = userRepository.findAll().stream().map(x -> new ResponseUserDTO(x.getUsername(), x.getName(), x.getApresentation(), x.getAddress().getCidade(), x.getAddress().getEstado())).toList();
        if (userList.isEmpty()) {
            throw new UserListIsEmptyException("Empty List");
        }
        return new ResponseAllUsersDTO(userList);
    }

    //find user

    public ResponseUserDTO readUserBy(String username){
        User user = userRepository.findUserByUsername(username);
            return new ResponseUserDTO(
                user.getUsername(),
                user.getName(),
                user.getApresentation(),
                user.getAddress().getCidade(),
                user.getAddress().getEstado());
    }

    // delete user
    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(getUserByUsername(username).getUsername());
    }

    //update user
    public ResponseUserDTO updateUserByUsername(String username, RequestUserDTO data) {
        User user = getUserByUsername(username);
        Address address = addressService.getAddress(data.cep());
        
        user.setUsername(data.username());
        user.setName(data.name());
        user.setApresentation(data.apresentation());
        user.setAddress(address);
        userRepository.save(user);
        return new ResponseUserDTO(user.getUsername(), user.getName(), user.getApresentation(), user.getAddress().getBairro(), user.getAddress().getEstado());
    }
    private User getUserByUsername(String username) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findUserByUsername(username));
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        return optionalUser.get();
    }
}