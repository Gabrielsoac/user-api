package com.user_api.services;

import com.user_api.clients.CEPClient;
import com.user_api.exceptions.AddressNotFoundException;
import com.user_api.model.entities.Address;
import com.user_api.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    private final CEPClient cepClient;

    public Address getAddress(String cep) {
        Address address = new Address(cepClient.getAddress(cep));
        if (address == null){
            throw new AddressNotFoundException("Address not found");
        }
        return address;
    }

    public void saveAddress(Address address) {
        if (addressRepository.existsById(address.getCep())){
            return;
        }
        addressRepository.save(address);
    }
}
