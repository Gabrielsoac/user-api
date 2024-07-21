package com.user_api.DTOs;

public record ResponseAddress(
        String cep,
        String bairro,
        String cidade,
        String estado,
        String logradouro)
{}
