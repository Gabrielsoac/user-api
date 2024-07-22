package com.user_api.DTOs;

import com.user_api.model.entities.Address;

public record ResponseUserDTO (String username, String name, String email, Address address){
}
