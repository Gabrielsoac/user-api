package com.user_api.services;

import com.user_api.DTOs.RequestUserDTO;
import com.user_api.DTOs.ResponseAllUsersDTO;
import com.user_api.DTOs.ResponseUserDTO;

public interface UserService {
    
    ResponseAllUsersDTO readAllUsers();
    ResponseUserDTO readUserBy(String username);
    ResponseUserDTO createUser(RequestUserDTO data);
    ResponseUserDTO updateUserByUsername(String username, RequestUserDTO data);
    void deleteUser(String username);
    
    


}
