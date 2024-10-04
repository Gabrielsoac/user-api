package com.user_api.services.Impl;

import com.user_api.infra.clients.CEPClient;
import com.user_api.model.entities.Address;
import com.user_api.repositories.AddressRepository;
import com.user_api.services.AddressService;

import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;

    private final CEPClient cepClient;

    public AddressServiceImpl(AddressRepository addressRepository, CEPClient cepClient){
        this.addressRepository = addressRepository;
        this.cepClient = cepClient;
    }

    @Override
    public Address getAddress(String cep) {
        Address address = new Address(cepClient.getAddress(cep));
        saveAddress(address);
        return address;
    }

    private void saveAddress(Address address) {
        if(!addressRepository.existsById(address.getCep())){
            addressRepository.save(address);
        }
    }
}
