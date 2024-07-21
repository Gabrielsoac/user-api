package com.user_api.DTOs;

import com.user_api.model.entities.Address;

public record ResponseUserDTO (String name, String email, Address address){
}
