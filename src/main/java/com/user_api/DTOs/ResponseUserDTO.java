package com.user_api.DTOs;

public record ResponseUserDTO (
        String username,
        String name,
        String apresentation,
        String cidade,
        String estado){

    @Override
    public String toString(){
        return "New user registered! :D \n" +
                "Welcome " + username + 
                "Apresentation: " + apresentation + "\n" +
                "City: " + cidade + "State: " + estado;
    }
}
