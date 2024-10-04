package com.user_api.controllers;

import com.user_api.DTOs.RequestUserDTO;
import com.user_api.DTOs.ResponseAllUsersDTO;
import com.user_api.DTOs.ResponseUserDTO;
import com.user_api.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseUserDTO> createUser(@RequestBody RequestUserDTO data) {
        return new ResponseEntity<>(userService.createUser(data), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseAllUsersDTO> readAllUsers() {
        return ResponseEntity.ok().body(userService.readAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseUserDTO> readUserByUsername(@PathVariable String username){
        return ResponseEntity.ok().body(userService.readUserBy(username));
    }

    @PutMapping("/{username}")
    @Transactional
    public ResponseEntity<ResponseUserDTO> updateUser(@PathVariable String username, @RequestBody RequestUserDTO data) {
        return ResponseEntity.ok().body(userService.updateUserByUsername(username, data));
    }

    @DeleteMapping("/{username}")
    @Transactional
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

}
