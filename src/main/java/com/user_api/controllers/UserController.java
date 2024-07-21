package com.user_api.controllers;

import com.user_api.DTOs.RequestUserDTO;
import com.user_api.DTOs.ResponseAllUsersDTO;
import com.user_api.DTOs.ResponseUserDTO;
import com.user_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ResponseAllUsersDTO> getAllUsers() {
        return ResponseEntity.ok().body(userService.findAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseUserDTO> getUserByUsername(@PathVariable String username){
        return ResponseEntity.ok().body(userService.findUserByUsername(username));
    }

    @PostMapping
    public ResponseEntity<ResponseUserDTO> createUser(@RequestBody RequestUserDTO data) {
        return new ResponseEntity<>(userService.saveNewUser(data), HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @PutMapping("/{username}")
    @Transactional
    public ResponseEntity<ResponseUserDTO> updateUser(@PathVariable String username, @RequestBody RequestUserDTO data) {
        return ResponseEntity.ok().body(userService.updateUserByUserName(username, data));
    }


}
