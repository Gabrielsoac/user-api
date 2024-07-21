package com.user_api.DTOs;

public record ResponseAddressDTO(
        String cep,
        String bairro,
        String cidade,
        String estado,
        String logradouro)
{}
