package com.user_api.controllers;

import com.user_api.DTOs.RequestUser;
import com.user_api.model.entities.User;
import com.user_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.findAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        return ResponseEntity.ok().body(userService.findUserByUsername(username));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody RequestUser data) {
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
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody RequestUser data) {
        return ResponseEntity.ok().body(userService.updateUserByUserName(username, data));
    }


}
