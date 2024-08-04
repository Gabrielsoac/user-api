package com.user_api.DTOs;

import com.user_api.model.entities.Address;

public record ResponseUserDTO (
        String username,
        String name,
        String apresentation,
        String cidade,
        String estado){

    @Override
    public String toString(){
        return "Novo usuário cadastrado \n" +
                "Nome de usuário: " + username + "\n" +
                "Nome: " + name + "\n" +
                "Apresentação: " + apresentation + "\n" +
                "Localidade: " + String.format("%s/%s", cidade, estado);

    }
}
