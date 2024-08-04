package com.user_api.services;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final TelegramClient telegramClient;
    private final String chat_id = "-1002169688714";

    //save new user
    public ResponseUserDTO saveNewUser(RequestUserDTO data) {
        
        if (userRepository.existsByUsername(data.username())){
            throw new UsernameAlreadyExistsException("Username Already Exists");
        }
        
        Address address = addressService.getAddress(data.cep());
        addressService.saveAddress(address);
        User newUser = new User(data.username(), data.name(), data.apresentation(), address);

        userRepository.save(newUser);

        ResponseUserDTO responseUserDTO =  new ResponseUserDTO(
        newUser.getUsername(),
        newUser.getName(),
        newUser.getApresentation(),
        newUser.getAddress().getCidade(),
        newUser.getAddress().getEstado());

        telegramClient.sendMessage(new RequestTelegramMessageDTO(chat_id, responseUserDTO.toString()));

        return responseUserDTO;

    }

    //find all users
    public ResponseAllUsersDTO findAllUsers() {
        List<ResponseUserDTO> userList = userRepository.findAll().stream().map(x -> new ResponseUserDTO(x.getUsername(), x.getName(), x.getApresentation(), x.getAddress().getCidade(), x.getAddress().getEstado())).toList();
        if (userList.isEmpty()) {
            throw new UserListIsEmptyException("Haven't Users for to List");
        }
        return new ResponseAllUsersDTO(userList);
    }

    //find user

    public ResponseUserDTO findUserBy(String username){
        User user = userRepository.findUserByUsername(username);
            return new ResponseUserDTO(
                user.getUsername(),
                user.getName(),
                user.getApresentation(),
                user.getAddress().getCidade(),
                user.getAddress().getEstado());
    }

    // delete user
    public void deleteUserByUsername(String username) {
        userRepository.deleteUserByUsername(getUserByUsername(username).getUsername());
    }

    //update user
    public ResponseUserDTO updateUserByUserName(String username, RequestUserDTO data) {
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