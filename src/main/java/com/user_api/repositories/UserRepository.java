package com.user_api.repositories;

import com.user_api.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findUserByUsername(String username);
    void deleteUserByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
