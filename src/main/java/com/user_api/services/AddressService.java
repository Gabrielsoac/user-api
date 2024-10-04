package com.user_api.services;

import com.user_api.model.entities.Address;

public interface AddressService {
    
    public Address getAddress(String cep);
}
